package com.shopngo.auction.service;

import com.shopngo.auction.service.dao.repository.AuctionRepository;
import com.shopngo.auction.service.domain.AuctionModel;
import com.shopngo.auction.service.strategy.AuctionEvaluationStrategy;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
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

    public List<AuctionModel> getAllAuctions() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public Optional<AuctionModel> getAuction(String id) {
        return repository.findById(id).map(converter::convert);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
