package com.shopngo.auction.item.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Getter
public class ItemModel {
    private final String id;
    private final String name;
    private final BigDecimal originalPrice;
    private final String currency;
    private final String description;
    private final String brand;
    private final String url;
}
