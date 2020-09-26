package com.shopngo.auction.service.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class BidModel {
    private final String id;
    private final String auctionId;
    private final BigDecimal bid;
    private final String originalCurrency;
    private final BigDecimal exchangeRate;
    private final long timestamp;
}
