package com.cy.rememeber.service;

import com.cy.rememeber.Exception.ErrorCode;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class UserQueueService {

    private final RedisTemplate<String, String> redisTemplate; //key:userId, value: unix timestamp 등록시점
    private final String USER_QUEUE_WAIT_KEY = "users:queue:%s:wait"; //큐를 여러개 운용할 수 있도록 가변값
    private final String USER_QUEUE_WAIT_KEY_FOR_SCAN = "users:queue:*:wait";
    private final String USER_QUEUE_PROCEED_KEY = "users:queue:%s:proceed"; //허용한 것들

    @Value("${scheduler.enabled}")
    private Boolean scheduling = false;

    // 대기열 등록
    public Long registerWaitQueue(final String queue, final Long userId) {
        var unixTimestamp = Instant.now().getEpochSecond();
        Boolean added = redisTemplate.opsForZSet()
            .add(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString(), unixTimestamp);

        if (Boolean.FALSE.equals(added)) {
            throw ErrorCode.QUEUE_ALREADY_REGISTERED_USER.build();
        }

        Long rank = redisTemplate.opsForZSet()
            .rank(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString());

        return (rank != null && rank >= 0) ? rank + 1 : -1;
    }

    /**
     * @Description 진입허용
     * @Param count: 몇 명의 사용자를 허용할 것인지
     */
    public Long allowUser(final String queue, final Long count) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String waitKey = USER_QUEUE_WAIT_KEY.formatted(queue);
        String proceedKey = USER_QUEUE_PROCEED_KEY.formatted(queue);

        //해당 큐에서 value값이 작은 멤버 빼줌 -> 사용자 추가 (허용된 현재 시간)
        Set<TypedTuple<String>> popped = zSetOps.popMin(waitKey, count);
        long allowedCount = 0;

        if (popped != null) {
            for (ZSetOperations.TypedTuple<String> member : popped) {
                if (member.getValue() != null) {
                    zSetOps.add(proceedKey, member.getValue(), Instant.now().getEpochSecond());
                    allowedCount++;
                }
            }
        }

        return allowedCount; //허용된 개수 return
    }

    /**
     * @Description 진입이 가능한 상태인지 확인
     * @Param
     */
    public boolean isAllowed(final String queue, final Long userId) {
        Long rank = redisTemplate.opsForZSet()
            .rank(USER_QUEUE_PROCEED_KEY.formatted(queue), userId.toString());
        return rank != null && rank >= 0; //rank>=0 이면 등록됐다고 간주
    }

    /**
     * @Description 진입이 가능한 상태인지 확인 by 토큰
     * @Param
     */
    public boolean isAllowedByToken(final String queue, final Long userId, final String token) {
        String generatedToken = this.generateToken(queue, userId);
        if (generatedToken.equalsIgnoreCase(token)) { //토큰비교
            return true;
        }
        return false;
    }

    /**
     * @Description 순번조회
     */
    public Long getRank(final String queue, final Long userId) {
        Long rank = redisTemplate.opsForZSet()
            .rank(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString());
        return (rank != null && rank >= 0) ? rank + 1 : -1;
//        return (rank != null && rank >= 0) ? rank + 1 : rank;
    }

    /**
     * @Description 토큰생성
     */
    public String generateToken(final String queue, final Long userId) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            var input = "user-queue-%s-%d".formatted(queue, userId);
            byte[] encodeHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte aByte : encodeHash) {
                hexString.append(String.format("%02x", aByte));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @Description 일정주기로 사용자 허용해주는 스케쥴
     * 5초후 첫번째 스케쥴링, 이후는 3초 주기로 진행
     * */
    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void scheduleAllowUser(){
        if(!scheduling) return;

        log.info("called scheduling.....");
        var maxAllowUserCount = 3L;

        // SCAN 명령어를 사용하기 위해 RedisTemplate을 통해 RedisConnection에 접근
        redisTemplate.execute((RedisCallback<Void>) connection -> {
            ScanOptions scanOptions = ScanOptions.scanOptions()
                    .match(USER_QUEUE_WAIT_KEY_FOR_SCAN)
                    .count(100) // 한 번에 가져올 키 개수
                    .build();

            //redis.scan() 호출
            try (Cursor<byte[]> cursor = connection.scan(scanOptions)) {
                while (cursor.hasNext()) {
                    String key = new String(cursor.next(), StandardCharsets.UTF_8);

                    String[] parts = key.split(":");
                    if (parts.length > 2) { //2번 인덱스가 큐 이름
                        String queueName = parts[2];
                        Long allowedCount = allowUser(queueName, maxAllowUserCount);
                        log.info("Tried {} and allowed {} members of {} queue", maxAllowUserCount, allowedCount, queueName);
                    }
                }
            }
            return null;
        });
    }

}
