package com.cy.rememeber.controller;

import com.cy.rememeber.dto.UserOauthInfoDto;
import com.cy.rememeber.service.OAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<?> kakaoCallBack(@RequestParam String code, HttpServletRequest request)
        throws JsonProcessingException {
        log.info("kakao code:{}", code);
        String accessToken = oAuthService.getKakaoAccessToken(code);
        UserOauthInfoDto userInfo = oAuthService.getUserInfo(accessToken);

        UserOauthInfoDto userOauthInfoDto = oAuthService.checkRegistedUser(userInfo);
        return ResponseEntity.ok(userOauthInfoDto);
    }


}
