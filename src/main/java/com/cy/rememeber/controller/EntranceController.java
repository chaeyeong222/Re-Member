package com.cy.rememeber.controller;

import com.cy.rememeber.dto.response.UserStatusResponseDto;
import com.cy.rememeber.service.UserQueueService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enter")
@RequiredArgsConstructor
public class EntranceController {
    private final UserQueueService userQueueService;

    @GetMapping("/checkStatus")
    public ResponseEntity<UserStatusResponseDto> checkStatus(
        @RequestParam(name = "queue", defaultValue = "default") String queue,
        @RequestParam(name = "user_id") Long userId,
        @RequestParam(name = "store_key") String storeKey,
        HttpServletRequest request) {

        System.out.println("get request :  userId "+ userId);
        var cookies = request.getCookies();
        var cookieName = "user-queue-%s-token".formatted(queue);

        System.out.println(cookieName + "                 cookieName,  queuename");
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

        System.out.println("isallowed? "+ isAllowed);
        if (isAllowed) {
            String bookingUrl = "/booking/" + storeKey;
            return ResponseEntity.ok(
                    new UserStatusResponseDto(true, bookingUrl)
            );
        } else {
//            String finalRedirectUrl = "http://localhost:3000/booking/" + storeKey;
//            String waitingRoomUrl = "http://localhost:3000/waiting-room?queue=%s&user_id=%d&redirect_url=%s".formatted(
//                    queue, userId, finalRedirectUrl);
            String finalRedirectUrl = "/booking/" + storeKey;
            String waitingRoomUrl = "/waiting-room?queue=%s&user_id=%d&redirect_url=%s".formatted(
                    queue, userId, finalRedirectUrl);
            return ResponseEntity.ok(
                    new UserStatusResponseDto(false, waitingRoomUrl)
            );

        }
    }
}