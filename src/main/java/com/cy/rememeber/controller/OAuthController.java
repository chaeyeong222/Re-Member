package com.cy.rememeber.controller;

import com.cy.rememeber.dto.request.KakaoLoginRequestDto;
import com.cy.rememeber.dto.response.LoginResponseDto;
import com.cy.rememeber.service.OAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/kakao")
@CrossOrigin(origins = "http://localhost:3000, https://re-member.store", allowedHeaders = "*")
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/check")
    public String check(){
        System.out.println("enter check");
        return "OK";
    }
    @PostMapping("/login")
    public ResponseEntity<?> kakaoLogin(@RequestBody KakaoLoginRequestDto requestDto) {
        System.out.println("enter");
        try {
            String code = requestDto.getCode();
            log.info("Kakao login request received with code: {}", code);
            LoginResponseDto loginResponse = oAuthService.kakaoLogin(code);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            log.error("Failed to process Kakao login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"Failed to process Kakao login.\"}");
        }
    }



}
