package com.shopngo.auction.service;

import com.shopngo.auction.service.dao.entity.AuctionEntity;
import com.shopngo.auction.service.dao.repository.AuctionRepository;
import com.shopngo.auction.service.domain.AuctionModel;
import com.shopngo.auction.service.domain.BidModel;
import com.shopngo.auction.service.strategy.AuctionEvaluationStrategy;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public class AuctionService {

    private final AuctionConverterService converter;
    private final AuctionRepository repository;
    private final BidService bidService;
    private final AuctionEvaluationStrategy strategy;

    public void createAuction(AuctionModel auction) {
        repository.save(converter.convert(auction));
    }

    public void placeBid(BidModel model) {
        bidService.placeBid(model);
    }

    public List<AuctionModel> getAllAuctions() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public Optional<AuctionModel> getAuction(String id) {
        return repository.findById(id).map(converter::convert);
    }

    public Map<String, List<BidModel>> getAllAuctionsWithBids() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(AuctionEntity::getId)
                .collect(Collectors.toMap(Function.identity(), bidService::getAllBidsForAuction));
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
