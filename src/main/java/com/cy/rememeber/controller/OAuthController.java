package com.cy.rememeber.controller;

import com.cy.rememeber.dto.UserOauthInfoDto;
import com.cy.rememeber.service.OAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
@CrossOrigin
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("")
    public ResponseEntity<UserOauthInfoDto> kakaoCallBack(@RequestParam String code)
        throws JsonProcessingException {
        log.info("Kakao callback code received: {}", code);

        String accessToken = oAuthService.getKakaoAccessToken(code); //카카오로부터 access token 받음
        UserOauthInfoDto userInfo = oAuthService.getUserInfo(accessToken); //사용자 정보 가져옴
        UserOauthInfoDto result = oAuthService.checkRegistedUser(userInfo); //기존회원인지 체크

        if (result.isUser()) {
            // 기존 회원: 로그인 성공 처리
            log.info("Existing user found. Social ID: {}", result.getSocial_id());
            // TODO: JWT 토큰 발급 로직 추가
            return ResponseEntity.ok(result); // 200 OK
        } else {
            // 신규 회원: 추가 정보 입력 유도
            log.info("New user. Needs registration. Social ID: {}", result.getSocial_id());
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED); // 202 Accepted
        }
    }


}
