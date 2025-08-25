package com.cy.rememeber.service;

import com.cy.rememeber.Exception.ErrorCode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueueService {
    private final RedisTemplate<String, String> redisTemplate;
    private final String USER_QUEUE_WAIT_KEY = "users:queue:%s:wait";
    private final String USER_QUEUE_WAIT_KEY_FOR_SCAN = "users:queue:*:wait";
    private final String USER_QUEUE_PROCEED_KEY = "users:queue:%s:proceed";

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
     * */
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

        return allowedCount;
    }

    /**
     * @Description 진입이 가능한 상태인지 확인
     * @Param
     * */
    public boolean isAllowed(final String queue, final Long userId) {
        Long rank = redisTemplate.opsForZSet()
            .rank(USER_QUEUE_PROCEED_KEY.formatted(queue), userId.toString());
        return rank != null && rank >= 0; //rank>=0 이면 등록됐다고 간주
    }

    /**
     * @Description 순번조회
     * */
    public Long getRank(final String queue, final Long userId) {
        Long rank = redisTemplate.opsForZSet()
                .rank(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString());
        return (rank != null && rank >= 0) ? rank + 1 : -1;
//        return (rank != null && rank >= 0) ? rank + 1 : rank;
    }

    /**
     * @Description 토큰생성
     * */
    public String generateToken(final String queue, final Long userId){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            var input = "user-queue-%s-%d".formatted(queue, userId);
            byte[] encodeHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for(byte aByte : encodeHash){
                hexString.append(String.format("%02x", aByte));
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

}
