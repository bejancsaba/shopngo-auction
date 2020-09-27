package com.shopngo.auction.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "shopngo")
public class BusinessProperties {

    private List<String> countries;
    private Map<String, String> countryToCurrencyMap;
    private Map<String, String> rates;
    private List<String> auctionTypes;
}
