package com.shopngo.auction.service;

import com.shopngo.auction.service.dao.entity.BidEntity;
import com.shopngo.auction.service.domain.BidModel;
import com.shopngo.auction.user.domain.UserModel;
import com.shopngo.auction.user.serice.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BidConverterService {

    private final UserService userService;

    public BidEntity convert(BidModel model) {
        return BidEntity.builder()
                .id(model.getId())
                .bidder(model.getBidder())
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
                .bidder(userService.getUserById(entity.getBidder()).map(UserModel::getName).orElse("N/A"))
                .auctionId(entity.getAuctionId())
                .bid(entity.getBid())
                .exchangeRate(entity.getExchangeRate())
                .originalCurrency(entity.getOriginalCurrency())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
