package com.cy.rememeber.service;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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

//        if (Boolean.FALSE.equals(added)) {
//            throw ErrorCode.QUEUE_ALREADY_REGISTERED_USER.build();
//        }

        Long rank = redisTemplate.opsForZSet()
            .rank(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString());

        return (rank != null && rank >= 0) ? rank + 1 : -1;
    }

}
