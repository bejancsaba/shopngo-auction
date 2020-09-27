package com.shopngo.auction.portal.controller;

import com.shopngo.auction.item.domain.ItemModel;
import com.shopngo.auction.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("@securityService.hasPermission('READ')")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ItemModel> getItems() {
        log.info("Getting all items");
        return itemService.getItems();
    }
}
