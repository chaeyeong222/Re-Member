package com.cy.rememeber.controller;

import com.cy.rememeber.dto.response.RegisterUserResponse;
import com.cy.rememeber.service.UserQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/queue")
@RequiredArgsConstructor
public class UserQueueController {
    private final UserQueueService userQueueService;
    // 등록할 수 잇는 api path
    @PostMapping("")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestParam(name = "queue", defaultValue = "default")
        String queue, @RequestParam(name = "user_id") String userId){

        Long rank = userQueueService.registerWaitQueue(queue, userId);
        return ResponseEntity.ok(new RegisterUserResponse(rank));
    }
}
