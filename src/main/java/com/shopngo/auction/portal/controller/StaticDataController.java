package com.shopngo.auction.portal.controller;

import com.shopngo.auction.config.BusinessProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/static")
public class StaticDataController {

    private final BusinessProperties properties;

    @GetMapping("/countries")
    public List<String> getCountries() {
        return properties.getCountries();
    }

    @GetMapping("/countryToCurrencyMap")
    public Map<String, String> getCurrencies() {
        return properties.getCountryToCurrencyMap();
    }

    @GetMapping("/rates")
    public Map<String, String> getRates() {
        return properties.getRates();
    }

    @GetMapping("/auctionTypes")
    public List<String> getAuctionTypes() {
        return properties.getAuctionTypes();
    }
}
