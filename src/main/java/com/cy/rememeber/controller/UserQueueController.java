package com.cy.rememeber.controller;

import com.cy.rememeber.dto.response.AllowUserResponse;
import com.cy.rememeber.dto.response.RegisterUserResponse;
import com.cy.rememeber.service.UserQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/queue")
@RequiredArgsConstructor
public class UserQueueController {
    private final UserQueueService userQueueService;

    /**
     * @Description 등록할 수 있는 api path
     * */
    @PostMapping("")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestParam(name = "queue", defaultValue = "default")
        String queue, @RequestParam(name = "user_id") Long userId){

        Long rank = userQueueService.registerWaitQueue(queue, userId);
        return ResponseEntity.ok(new RegisterUserResponse(rank));
    }

    /**
     * @Description 등록가능한지 확인
     * */
    @PostMapping("/allow")
    public ResponseEntity<AllowUserResponse> allowUser(@RequestParam(name = "queue", defaultValue = "default") String queue,
    @RequestParam(name = "count") Long count) {
        Long allowed = userQueueService.allowUser(queue, count);
        return ResponseEntity.ok(new AllowUserResponse(count, allowed));
    }
}
