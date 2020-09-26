package com.shopngo.auction.item.service;

import com.shopngo.auction.item.dao.entity.ItemEntity;
import com.shopngo.auction.item.domain.ItemModel;
import com.shopngo.auction.user.dao.entity.UserEntity;
import com.shopngo.auction.user.domain.UserModel;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

public class ItemConverterService {

    public ItemEntity convert(ItemModel model) {
        return ItemEntity.builder()
                .id(model.getId())
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
                .brand(entity.getBrand())
                .currency(entity.getCurrency())
                .description(entity.getDescription())
                .originalPrice(entity.getOriginalPrice())
                .url(entity.getUrl())
                .build();
    }
}