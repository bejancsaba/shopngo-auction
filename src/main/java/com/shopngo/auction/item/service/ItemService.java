package com.shopngo.auction.item.service;

import com.shopngo.auction.item.dao.repository.ItemRepository;
import com.shopngo.auction.item.domain.ItemModel;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ItemService {

    private final ItemConverterService converter;
    private final ItemRepository repository;

    public Optional<ItemModel> getItem(String id) {
        return repository.findById(id).map(converter::convert);
    }
}
