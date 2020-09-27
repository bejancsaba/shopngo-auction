package com.shopngo.auction.service.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder(toBuilder = true)
@Getter
public class AuctionModel {
    private final String id;
    private final String type;
    private final String ownerId;
    private final String ownerName;
    private final String itemId;
    private final String itemName;
    private final String description;
    private final List<String> targetCountries;
    private final List<String> tags;
    private final BigDecimal startingBid;
    private final long startDate;
    private final long endDate;
    private final String statusMessage;
}
