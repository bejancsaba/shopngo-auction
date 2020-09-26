package com.shopngo.auction.service.strategy;

import com.shopngo.auction.service.BidService;
import com.shopngo.auction.service.domain.AuctionModel;
import com.shopngo.auction.service.domain.BidModel;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.Optional;

/**
 * Strategy to determine which bid is winning.
 * - Bid timestamp should be between auction start and end date
 * - Bid amount should be the highest among all the other valid bids (and should be the fastest in case of tie)
 */
@RequiredArgsConstructor
public class DefaultAuctionEvaluationStrategy implements AuctionEvaluationStrategy {

    private final BidService bidService;

    @Override
    public Optional<BidModel> execute(AuctionModel auction) {
        return bidService.getAllBidsForAuction(auction.getId()).stream()
                .filter(bid -> between(bid.getTimestamp(), auction.getStartDate(), auction.getEndDate()))
                .filter(bid -> bid.getBid().compareTo(auction.getStartingBid()) >= 0)
                .max(Comparator.comparing(BidModel::getBid));
    }

    private boolean between(long timestamp, long startDate, long endDate) {
        return startDate <= timestamp && timestamp <= endDate;
    }
}
