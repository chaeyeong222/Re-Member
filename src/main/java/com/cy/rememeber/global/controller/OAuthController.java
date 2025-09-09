package com.cy.rememeber.global.controller;

import com.cy.rememeber.dto.UserOauthInfoDto;

import com.cy.rememeber.service.OAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
@CrossOrigin
public class OAuthController {

    private final OAuthService oAuthService;

    /**
     * 카카오 callback [GET] /oauth/kakao/callback
     */
    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        log.info("kakao code : {} ", code);
        String accessToken = oAuthService.getKakaoAccessToken(code);
        UserOauthInfoDto userInfo = oAuthService.getUserInfo(accessToken);

        UserOauthInfoDto userOauthInfoDto = oAuthService.checkRegistedUser(userInfo, response);
        return ResponseEntity.ok(userOauthInfoDto);
    }
}