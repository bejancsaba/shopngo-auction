package com.shopngo.auction.service.dao.repository;

import com.shopngo.auction.service.dao.entity.AuctionEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuctionRepository extends CrudRepository<AuctionEntity, String> {
}
