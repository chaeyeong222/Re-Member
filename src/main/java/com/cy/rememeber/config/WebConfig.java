package com.cy.rememeber.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins( "http://localhost:3000", "https://re-member-zeta.vercel.app")
            .allowedMethods("*")
            .allowedHeaders("*")
            .exposedHeaders("*")
            .allowedOriginPatterns("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}