package com.shopngo.auction.service;

import com.shopngo.auction.service.dao.repository.AuctionRepository;
import com.shopngo.auction.service.domain.AuctionModel;
import com.shopngo.auction.service.strategy.AuctionEvaluationStrategy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuctionService {

    private final AuctionConverterService converter;
    private final AuctionRepository repository;
    private final BidService bidService;
    private final AuctionEvaluationStrategy strategy;

    public void createAuction(AuctionModel auction) {
        repository.save(converter.convert(auction));
    }

}
