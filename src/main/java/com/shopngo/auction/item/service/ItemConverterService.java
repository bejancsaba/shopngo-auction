package com.shopngo.auction.item.service;

import com.shopngo.auction.item.dao.entity.ItemEntity;
import com.shopngo.auction.item.domain.ItemModel;

public class ItemConverterService {

    public ItemEntity convert(ItemModel model) {
        return ItemEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .brand(model.getBrand())
                .currency(model.getCurrency())
                .description(model.getDescription())
                .originalPrice(model.getOriginalPrice())
                .url(model.getUrl())
                .build();
    }

    public ItemModel convert(ItemEntity entity) {
        return ItemModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .currency(entity.getCurrency())
                .description(entity.getDescription())
                .originalPrice(entity.getOriginalPrice())
                .url(entity.getUrl())
                .build();
    }
}