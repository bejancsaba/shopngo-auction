package com.shopngo.auction.config;

import com.shopngo.auction.authentication.service.AuthenticationService;
import com.shopngo.auction.authentication.service.DefaultAuthenticationService;
import com.shopngo.auction.authentication.service.JwtBuilderService;
import com.shopngo.auction.item.dao.repository.ItemRepository;
import com.shopngo.auction.item.service.ItemConverterService;
import com.shopngo.auction.item.service.ItemService;
import com.shopngo.auction.service.AuctionConverterService;
import com.shopngo.auction.service.AuctionService;
import com.shopngo.auction.service.BidConverterService;
import com.shopngo.auction.service.BidService;
import com.shopngo.auction.service.dao.repository.AuctionRepository;
import com.shopngo.auction.service.dao.repository.BidRepository;
import com.shopngo.auction.service.strategy.AuctionEvaluationStrategy;
import com.shopngo.auction.service.strategy.DefaultAuctionEvaluationStrategy;
import com.shopngo.auction.user.dao.repository.UserRepository;
import com.shopngo.auction.user.serice.UserConverterService;
import com.shopngo.auction.user.serice.UserService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.shopngo"})
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
    public ItemConverterService itemConverterService() {
        return new ItemConverterService();
    }

    @Bean
    public ItemService itemService(ItemConverterService converterService, ItemRepository repository) {
        return new ItemService(converterService, repository);
    }

    @Bean
    public BidConverterService bidConverterService(UserService userService) {
        return new BidConverterService(userService);
    }

    @Bean
    public BidService bidService(BidConverterService converterService, BidRepository repository) {
        return new BidService(converterService, repository);
    }

    @Bean
    public AuctionConverterService auctionConverterService(UserService userService, ItemService itemService) {
        return new AuctionConverterService(userService, itemService);
    }

    @Bean
    public AuctionEvaluationStrategy auctionEvaluationStrategy(BidService bidService) {
        return new DefaultAuctionEvaluationStrategy(bidService);
    }

    @Bean
    public AuctionService auctionService(AuctionConverterService converterService,
                                         AuctionRepository repository,
                                         BidService bidService,
                                         AuctionEvaluationStrategy strategy) {
        return new AuctionService(converterService, repository, bidService, strategy);
    }

    @Bean
    public AuthenticationService authenticationService(UserService userService, JwtBuilderService jwtBuilderService) {
        return new DefaultAuthenticationService(userService, jwtBuilderService);
    }
}
