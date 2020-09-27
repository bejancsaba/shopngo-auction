package com.shopngo.auction.service;

import com.shopngo.auction.service.dao.entity.AuctionEntity;
import com.shopngo.auction.service.dao.repository.AuctionRepository;
import com.shopngo.auction.service.domain.AuctionModel;
import com.shopngo.auction.service.domain.BidModel;
import com.shopngo.auction.service.strategy.AuctionEvaluationStrategy;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
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
                .map(this::decorateAuctionStatus)
                .collect(Collectors.toList());
    }

    public Optional<AuctionModel> getAuction(String id) {
        return repository.findById(id).map(converter::convert);
    }

    public Map<String, List<BidModel>> getAllAuctionsWithBids() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(AuctionEntity::getId)
                .collect(Collectors.toMap(Function.identity(), auctionId -> {
                    List<BidModel> bids = bidService.getAllBidsForAuction(auctionId);
                    return decorateBidsWithStatusMessage(auctionId, bids);
                }));
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    // ---------------
    // These decorations are just for demo purposes but we shouldn't do this in production
    // We should properly implement the standalone and persistent execution logic
    // ---------------
    private AuctionModel decorateAuctionStatus(AuctionModel model) {
        String message = "N/A";
        long now = Instant.now().toEpochMilli();

        if (now < model.getStartDate()) {
            message = "NOT STARTED";
        }
        else if (now > model.getEndDate()) {
            message = "CLOSED";
        }
        else {
            message = "ONGOING";
        }

        return model.toBuilder().statusMessage(message).build();
    }

    private List<BidModel> decorateBidsWithStatusMessage(String auctionId, List<BidModel> bids) {
        List<BidModel> decorated = new ArrayList<>();
        AuctionModel auction = getAuction(auctionId).get();
        long auctionStart = auction.getStartDate();
        long auctionEnd = auction.getEndDate();
        bids.sort(Comparator.comparing(BidModel::getTimestamp));

        BidModel winning = null;
        for (BidModel bid: bids) {
            if (bid.getTimestamp() >= auctionStart
                && bid.getTimestamp() <= auctionEnd
                && bid.getBid().compareTo(auction.getStartingBid()) > 0) {
                if (winning == null || bid.getBid().compareTo(winning.getBid()) > 0) {
                    winning = bid;
                }
            }
        }

        for (BidModel bid: bids) {
            BidModel decoratedBid = bid;

            if (auction.getStartingBid().compareTo(bid.getBid()) >= 0) {
                decoratedBid = bid.toBuilder().statusMessage("TOO CHEAP").build();
            }

            if (bid.getTimestamp() < auctionStart) {
                decoratedBid = bid.toBuilder().statusMessage("TOO EARLY").build();
            }
            else if (bid.getTimestamp() > auctionEnd) {
                decoratedBid = bid.toBuilder().statusMessage("TOO LATE").build();
            }

            if (bid.equals(winning)) {
                decoratedBid = bid.toBuilder().statusMessage("WINNING").build();
            }

            decorated.add(decoratedBid);
        }

        return decorated;
    }
}
