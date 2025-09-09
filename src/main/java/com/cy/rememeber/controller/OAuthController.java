package com.cy.rememeber.controller;

import com.cy.rememeber.dto.UserOauthInfoDto;
import com.cy.rememeber.dto.request.KakaoTokenRequestDto;
import com.cy.rememeber.dto.request.KakaoUserRequestDto;
import com.cy.rememeber.dto.response.KakaoTokenResponseDto;
import com.cy.rememeber.service.OAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/kakao")
@CrossOrigin
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/token")
    public ResponseEntity<?> getKakaoToken(@RequestBody KakaoTokenRequestDto requestDto) {
        try {
            System.out.println("받아?");
            String code = requestDto.getCode();
            log.info("Kakao callback code received: {}", code);
            String accessToken = oAuthService.getKakaoAccessToken(code);
            return ResponseEntity.ok(new KakaoTokenResponseDto(accessToken));
        } catch (Exception e) {
            log.error("Failed to get Kakao access token", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"Failed to exchange token\"}");
        }
    }
    @PostMapping("/user")
    public ResponseEntity<UserOauthInfoDto> getKakaoUserInfo(@RequestBody KakaoUserRequestDto requestDto) {
        try {
            String accessToken = requestDto.getAccessToken();
            log.info("User info request received with access token");

            UserOauthInfoDto userInfo = oAuthService.getUserInfo(accessToken);
            UserOauthInfoDto result = oAuthService.checkRegistedUser(userInfo);

            if (result.isUser()) {
                log.info("Existing user found. Social ID: {}", result.getSocialId());
                return ResponseEntity.ok(result);
            } else {
                log.info("New user. Needs registration. Social ID: {}", result.getSocialId());
                return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            log.error("Failed to get user info or check user status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
