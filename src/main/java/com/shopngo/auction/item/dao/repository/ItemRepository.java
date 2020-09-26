package com.shopngo.auction.item.dao.repository;

import com.shopngo.auction.item.dao.entity.ItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<ItemEntity, String> {
}
