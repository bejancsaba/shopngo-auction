package com.shopngo.auction.service;

import com.shopngo.auction.service.dao.repository.BidRepository;
import com.shopngo.auction.service.domain.BidModel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BidService {

    private final BidConverterService converter;
    private final BidRepository repository;

    public List<BidModel> getAllBidsForAuction(String auctionId) {
        return repository.findBidEntitiesByAuctionId(auctionId).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public void placeBid(BidModel model) {
        repository.save(converter.convert(model));
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
