package com.shopngo.auction.item.service;

import com.shopngo.auction.item.dao.repository.ItemRepository;
import com.shopngo.auction.item.domain.ItemModel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public class ItemService {

    private final ItemConverterService converter;
    private final ItemRepository repository;

    public Optional<ItemModel> getItem(String id) {
        return repository.findById(id).map(converter::convert);
    }

    public List<ItemModel> getItems() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public void saveItem(ItemModel model) {
        repository.save(converter.convert(model));
    }

    public void deleteAllItems() {
        repository.deleteAll();
    }
}
