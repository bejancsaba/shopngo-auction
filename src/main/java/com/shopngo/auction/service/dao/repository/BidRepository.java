package com.shopngo.auction.service.dao.repository;

import com.shopngo.auction.service.dao.entity.BidEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BidRepository extends CrudRepository<BidEntity, String> {

    public List<BidEntity> findBidEntitiesByAuctionId(String auctionId);
}
