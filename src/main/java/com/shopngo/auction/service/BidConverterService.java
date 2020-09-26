package com.shopngo.auction.service;

import com.shopngo.auction.service.dao.entity.BidEntity;
import com.shopngo.auction.service.domain.BidModel;

public class BidConverterService {

    public BidEntity convert(BidModel model) {
        return BidEntity.builder()
                .id(model.getId())
                .auctionId(model.getAuctionId())
                .bid(model.getBid())
                .exchangeRate(model.getExchangeRate())
                .originalCurrency(model.getOriginalCurrency())
                .timestamp(model.getTimestamp())
                .build();
    }

    public BidModel convert(BidEntity entity) {
        return BidModel.builder()
                .id(entity.getId())
                .auctionId(entity.getAuctionId())
                .bid(entity.getBid())
                .exchangeRate(entity.getExchangeRate())
                .originalCurrency(entity.getOriginalCurrency())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
