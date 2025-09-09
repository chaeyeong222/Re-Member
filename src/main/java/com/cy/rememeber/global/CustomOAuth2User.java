package com.cy.rememeber.global;


import com.cy.rememeber.business.Entity.User.Role;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import lombok.Getter;

/**
 * DefaultOAuth2User를 상속하고, email과 role 필드를 추가로 가진다.
 */
@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private String phone;
    private Role role;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
        Map<String, Object> attributes, String nameAttributeKey,
        String phone, Role role) {
        super(authorities, attributes, nameAttributeKey);
        this.phone = phone;
        this.role = role;
    }
}