package com.cy.rememeber.service;

import com.cy.rememeber.Exception.ErrorCode;
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

    // 사용자 허용
    public Long allowUser(final String queue, final Long count) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String waitKey = USER_QUEUE_WAIT_KEY.formatted(queue);
        String proceedKey = USER_QUEUE_PROCEED_KEY.formatted(queue);

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

    // 허용 여부 확인
    public boolean isAllowed(final String queue, final Long userId) {
        Long rank = redisTemplate.opsForZSet()
            .rank(USER_QUEUE_PROCEED_KEY.formatted(queue), userId.toString());
        return rank != null && rank >= 0;
    }

}
