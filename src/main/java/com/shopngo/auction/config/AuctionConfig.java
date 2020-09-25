package com.shopngo.auction.config;

import com.shopngo.auction.identity.service.DefaultIdentityService;
import com.shopngo.auction.identity.service.IdentityService;
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
    public IdentityService identityService(UserService userService) {
        return new DefaultIdentityService(userService);
    }
}
