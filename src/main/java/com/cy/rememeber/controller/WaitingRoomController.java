package com.cy.rememeber.controller;

import com.cy.rememeber.service.UserQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; // API 컨트롤러로 바꾸기 위해 @RestController로 변경하는 것이 더 적합합니다.
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller 대신 @RestController를 사용합니다.
@RequiredArgsConstructor
public class WaitingRoomController {
    private final UserQueueService userQueueService;

    @GetMapping("/waiting-room")
    public ResponseEntity<?> waitingRoomPage(
        @RequestParam(name = "queue", defaultValue = "default") String queue,
        @RequestParam(name = "user_id") Long userId
    ){
        //대기열 등록 시도
        Long rank;
        try {
            userQueueService.registerWaitQueue(queue, userId);
            rank = 0L; // 성공적으로 등록되면 랭크를 0
        } catch (Exception ex) {
            // 예외가 발생하면 랭크만 가져오는 메서드를 호출합니다.
            rank = userQueueService.getRank(queue, userId);
        }
        WaitingRoomResponse response = new WaitingRoomResponse(rank, userId, queue);
        return ResponseEntity.ok(response);
    }

    // 응답 데이터 포맷을 정의합니다.
    public record WaitingRoomResponse(Long number, Long userId, String queue) {}

}