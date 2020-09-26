package com.shopngo.auction.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopngo.auction.authentication.service.JwtBuilderService;
import com.shopngo.auction.portal.security.JwtValidatorService;
import com.shopngo.auction.portal.security.SecurityService;
import com.shopngo.auction.portal.security.UnsuccessfulJwtAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTH_KEY_NAME = "auth";
    public static final String LOGIN_SESSION_KEY_NAME = "loginSession";
    public static final String EMAIL_KEY_NAME = "email";
    public static final String COUNTRY_KEY_NAME = "country";
    public static final String VERIFIED_USER_KEY_NAME = "verified";
    public static final String JWT_ISSUER = "ShopNGoIssuer";

    public static final Algorithm ALGORITHM = Algorithm.HMAC512("secret");

    @Bean
    public JwtValidatorService jwtValidatorService() {
        return new JwtValidatorService();
    }

    @Bean
    public JwtBuilderService jwtBuilderService() {
        return new JwtBuilderService();
    }

    @Bean
    public UnsuccessfulJwtAuthenticationFailureHandler unsuccessfulJwtAuthenticationFailureHandler(
            ObjectMapper objectMapper) {
        return new UnsuccessfulJwtAuthenticationFailureHandler(objectMapper);
    }

    @Bean
    public SecurityService securityService() {
        return new SecurityService();
    }
}
