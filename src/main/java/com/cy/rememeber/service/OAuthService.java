package com.cy.rememeber.service;

import com.cy.rememeber.Entity.User;
import com.cy.rememeber.dto.UserOauthInfoDto;
import com.cy.rememeber.dto.response.LoginResponseDto;
import com.cy.rememeber.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String secretKey;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    private final UserRepository userRepository; // 사용자 조회를 위해 UserRepository 주입

    /**
     * 인가 코드를 받아 카카오 로그인을 처리하는 통합 메소드
     */
    @Transactional
    public LoginResponseDto kakaoLogin(String code) throws JsonProcessingException {
        // 1. 인가 코드로 카카오 액세스 토큰 받기
        System.out.println(code +" code");
        String accessToken = getKakaoAccessToken(code);
        // 2. 액세스 토큰으로 카카오 사용자 정보 받기
        UserOauthInfoDto userInfo = getUserInfo(accessToken);

        String socialId = userInfo.getSocialId();

        // 3. 소셜 ID로 우리 DB에서 회원 조회
        return userRepository.findBySocialId(socialId)
            .map(user -> {
                // 3-1. 기존 회원인 경우
                log.info("기존 회원 로그인 : socialId={}", socialId);
                // JWT 관련 로직 제거

                // 응답 DTO 생성
                return LoginResponseDto.builder()
                    .newUser(false)
                    .socialId(user.getSocialId())
                    .nickname(user.getNickname())
                    .build();
            })
            .orElseGet(() -> {
                // 3-2. 신규 회원인 경우
                log.info("신규 회원. 회원가입 필요 : socialId={}", socialId);
                // 회원가입 페이지로 보낼 정보 응답
                return LoginResponseDto.builder()
                    .newUser(true)
                    .socialId(userInfo.getSocialId())
                    .nickname(userInfo.getNickname()) // 카카오 닉네임을 임시로 전달
                    .build();
            });
    }


    /**
     * 카카오 서버로 부터 Access 토큰값 받아오기
     */
    private String getKakaoAccessToken(String code) throws JsonProcessingException {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", secretKey);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = rt.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            String.class
        );
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    /**
     * token으로 카카오에서 유저데이터 가져오기
     */
    private UserOauthInfoDto getUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoUserInfoRequest,
            String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String socialId = jsonNode.get("id").asText();
        String nickname = jsonNode.get("properties").get("nickname").asText();

        UserOauthInfoDto userOauthInfoDto = new UserOauthInfoDto();
        userOauthInfoDto.setSocialId(socialId);
        userOauthInfoDto.setNickname(nickname);

        return userOauthInfoDto;
    }
}