package com.cy.rememeber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // CSRF 비활성화 (테스트용)
            .authorizeHttpRequests()
            .anyRequest().permitAll()  // 모든 요청 허용
            .and()
            .httpBasic().disable() // 기본 인증 비활성화 (선택)
            .formLogin().disable(); // 폼 로그인 비활성화 (선택)
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
