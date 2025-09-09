package com.cy.rememeber.global.oauth;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getPhone() {

        @SuppressWarnings("unchecked")
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        @SuppressWarnings("unchecked")
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if (account == null || profile == null) {
            return null;
        }

        return (String) profile.get("phone");
    }

//    @Override
//    public String getImageUrl() {
//
//        @SuppressWarnings("unchecked")
//        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
//        @SuppressWarnings("unchecked")
//        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
//
//        if (account == null || profile == null) {
//
//            return null;
//        }
//
//        return (String) profile.get("thumbnail_image_url");
//    }
}