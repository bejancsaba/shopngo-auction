package com.shopngo.auction.service.strategy;

import com.shopngo.auction.service.domain.AuctionModel;
import com.shopngo.auction.service.domain.BidModel;

import java.util.Optional;

public interface AuctionEvaluationStrategy {

    Optional<BidModel> execute(AuctionModel auction);
}
