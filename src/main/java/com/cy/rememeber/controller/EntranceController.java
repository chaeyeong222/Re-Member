package com.cy.rememeber.controller;

import com.cy.rememeber.dto.response.UserStatusResponseDto;
import com.cy.rememeber.service.UserQueueService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/enter")
@RequiredArgsConstructor
public class EntranceController {
    private final UserQueueService userQueueService;

    @GetMapping("/checkStatus")
    public ResponseEntity<UserStatusResponseDto> checkStatus(
        @RequestParam(name = "queue", defaultValue = "default") String queue,
        @RequestParam(name = "user_id") Long userId,
        HttpServletRequest request) {

        var cookies = request.getCookies();
        var cookieName = "user-queue-%s-token".formatted(queue);

        //쿠키 담겨있는 값들 중 큐이름에 해당하는 값을 가져옴
        String token = "";
        if (cookies != null) {
            var cookie = Arrays.stream(cookies)
                .filter(i -> i.getName().equalsIgnoreCase(cookieName))
                .findFirst();
            token = cookie.orElse(new Cookie(cookieName, "")).getValue();
        }

        //토큰 검사
        boolean isAllowed = userQueueService.isAllowedByToken(queue, userId, token);

        if (isAllowed) {
            return ResponseEntity.ok(
                new UserStatusResponseDto(true, "/reservations") //예약페이지로 이동
            );
        } else {
            String redirectUrl = "http://localhost:8090/waiting-room?user_id=%d&redirect_url=%s".formatted(
                userId, "localhost:8090/reservations"); //입장가능해지면 이동할 페이지 s 에 들어감
            return ResponseEntity.ok(
                new UserStatusResponseDto(false, redirectUrl)
            );
        }
    }
}