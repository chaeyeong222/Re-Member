package com.cy.rememeber.controller;

import com.cy.rememeber.dto.response.AllowUserResponse;
import com.cy.rememeber.dto.response.AllowedUserResponse;
import com.cy.rememeber.dto.response.RankNumberResponse;
import com.cy.rememeber.dto.response.RegisterUserResponse;
import com.cy.rememeber.service.UserQueueService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
@RequestMapping("api/v1/queue")
@RequiredArgsConstructor
public class UserQueueController {

    private final UserQueueService userQueueService;

    /**
     * @Description 등록할 수 있는 api path
     */
    @PostMapping("")
    public ResponseEntity<RegisterUserResponse> registerUser(
        @RequestParam(name = "queue", defaultValue = "default")
        String queue, @RequestParam(name = "user_id") Long userId) {

        Long rank = userQueueService.registerWaitQueue(queue, userId);
        return ResponseEntity.ok(new RegisterUserResponse(rank));
    }

    /**
     * @Description 진입허용
     */
    @PostMapping("/allow")
    public ResponseEntity<AllowUserResponse> allowUser(
        @RequestParam(name = "queue", defaultValue = "default") String queue,
        @RequestParam(name = "count") Long count) {
        Long allowed = userQueueService.allowUser(queue, count);
        return ResponseEntity.ok(new AllowUserResponse(count, allowed)); //몇개요청되었고, 실제 몇명 허용됐는지
    }

    /**
     * @Description 진입 가능한지 확인
     */
    @GetMapping("/allowed")
    public ResponseEntity<?> isAllowedUser(
        @RequestParam(name = "queue", defaultValue = "default") String queue,
        @RequestParam(name = "user_id") Long userId) {
        boolean allowed = userQueueService.isAllowed(queue, userId);
        return ResponseEntity.ok(new AllowedUserResponse(allowed));
    }

    /**
     * @Description 대기번호 체크
     */
    @GetMapping("/rank")
    public ResponseEntity<?> getRank(final String queue, final Long userId) {
        Long rank = userQueueService.getRank(queue, userId);
        return ResponseEntity.ok(new RankNumberResponse(rank));
    }

    /**
     * @Descriptioin 토큰 생성
     */
    @GetMapping("/touch")
    public ResponseEntity<?> touch(
        @RequestParam(name = "queue", defaultValue = "default") String queue,
        @RequestParam(name = "user_id") Long userId,
        HttpServletResponse response) {
        String token = userQueueService.generateToken(queue, userId);

        Cookie cookie = new Cookie("user-queue-%s-token".formatted(queue),token);
        cookie.setMaxAge((int) Duration.ofSeconds(300).toSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(token);
    }

}
