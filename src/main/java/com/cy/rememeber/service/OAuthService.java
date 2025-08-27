package com.cy.rememeber.service;

import com.cy.rememeber.dto.UserOauthInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class OAuthService {

//    @Value"${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String secreKey;

    @Autowired
    private StoreService storeService;

    public String getKakaoAccessToken(String code) throws JsonProcessingException {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", code);
//        params.add("client_secret", secretKey);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }


    public UserOauthInfoDto getUserInfo(String accessToken) throws JsonProcessingException {
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

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String socialId = jsonNode.get("id").asText();

        UserOauthInfoDto userOauthInfoDto = new UserOauthInfoDto();
        userOauthInfoDto.setSocial_id(socialId);
        return userOauthInfoDto;
    }

    /**
     * ���� ȸ������ üũ�ϴ� �޼���
     * - JWT �߱� ���� ȸ�� ���θ� Ȯ��
     */
//    public UserOauthInfoDto checkRegistedUser(UserOauthInfoDto userInfo) {
//        String socialId = userInfo.getSocial_id();
//        UserEntity user = userService.getUser(socialId);
//
//        UserOauthInfoDto result = new UserOauthInfoDto();
//        result.setSocial_id(socialId);
//
//        if (user != null) {
//            // ���� ȸ��
//            log.info("���� ȸ�� �α��� : {}", socialId);
//            result.setNickName(user.getNickname());
//            result.setUser(true);
//        } else {
//            // �ű� ȸ��: ȸ������ ó��(�ʿ� �� ��Ʈ�ѷ����� �߰�)
//            result.setNickName(userInfo.getNickName());
//            result.setUser(false);
//        }

//        return result;
//    }
}