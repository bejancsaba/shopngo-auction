package com.shopngo.auction.config;

import com.shopngo.auction.authentication.service.DefaultAuthenticationService;
import com.shopngo.auction.authentication.service.AuthenticationService;
import com.shopngo.auction.authentication.service.JwtBuilderService;
import com.shopngo.auction.user.dao.repository.UserRepository;
import com.shopngo.auction.user.serice.UserConverterService;
import com.shopngo.auction.user.serice.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.shopngo.auction")
public class AuctionConfig {

    @Bean
    public UserConverterService userConverterService() {
        return new UserConverterService();
    }

    @Bean
    public UserService userService(UserConverterService converterService, UserRepository repository) {
        return new UserService(converterService, repository);
    }

    @Bean
    public AuthenticationService identityService(UserService userService, JwtBuilderService jwtBuilderService) {
        return new DefaultAuthenticationService(userService, jwtBuilderService);
    }
}
