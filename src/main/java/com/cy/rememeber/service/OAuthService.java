package com.cy.rememeber.service;

import com.cy.rememeber.Entity.Store;
import com.cy.rememeber.Entity.User;
import com.cy.rememeber.dto.UserOauthInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

    @Autowired
    private final UserService userService;

    /**
     * @Description 카카오 서버로 부터 Access 토큰값 받아오기
     * */
    public String getKakaoAccessToken(String code) throws JsonProcessingException {

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded"); //body데이터 설명해주는 헤더

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code); //카카오 코드
        params.add("client_secret", secretKey);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token", // https://{요청할 서버 주소}
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String accessToken = jsonNode.get("access_token").asText();
        return accessToken;
    }

    /**
     * @Description  token으로 카카오에서 유저데이터 가져오기
     * */
    public UserOauthInfoDto getUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // HTTP 요청 보내기
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

        // 카카오에서 받은 닉네임과 ID를 모두 DTO에 담는다
        String socialId = jsonNode.get("id").asText();
        String nickname = jsonNode.get("properties").get("nickname").asText();

        UserOauthInfoDto userOauthInfoDto = new UserOauthInfoDto();
        userOauthInfoDto.setSocialId(socialId);
        userOauthInfoDto.setNickname(nickname);

        return userOauthInfoDto;
    }

    /**
     * @Description 기존회원여부 체크
     * @param userInfo 카카오에서 받아온 유저정보
     * @return UserOauthInfoDto
     */
    public UserOauthInfoDto checkRegistedUser(UserOauthInfoDto userInfo){
        String socialId = userInfo.getSocialId();
        UserOauthInfoDto userOauthInfoDto = new UserOauthInfoDto();
        userOauthInfoDto.setSocialId(socialId);

        // 소셜 ID로 사용자 검색
        User user = userService.getUser(socialId);
        if (user != null) { //이미 등록된 회원
            log.info("기존 회원 로그인 : {}", socialId);
            userOauthInfoDto.setPhoneNumber(user.getPhone());
            userOauthInfoDto.setName(user.getUserName());
            userOauthInfoDto.setUser(true);
        }else{
            log.info("New user. Social ID:{}", socialId);
            userOauthInfoDto.setSocialId(user.getSocialId());
            userOauthInfoDto.setUser(false); //신규회원
        }

        return userOauthInfoDto;
    }
}