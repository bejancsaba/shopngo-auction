package com.shopngo.auction.portal.controller;

import com.shopngo.auction.item.domain.ItemModel;
import com.shopngo.auction.item.service.ItemService;
import com.shopngo.auction.service.AuctionService;
import com.shopngo.auction.service.BidService;
import com.shopngo.auction.service.domain.AuctionModel;
import com.shopngo.auction.user.domain.UserModel;
import com.shopngo.auction.user.serice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * !!! Use only for test purposes to prepopulate data !!!
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Profile("!prod")
public class AdminController {

    private final UserService userService;
    private final AuctionService auctionService;
    private final BidService bidService;
    private final ItemService itemService;

    @GetMapping("/users/populate")
    public void populateUsers() {
        UserModel user1 = UserModel.builder()
                .name("user1")
                .password("password1")
                .permissions(Set.of("READ", "BID"))
                .isVerified(Boolean.FALSE)
                .email("user1@shopngo.com")
                .build();

        UserModel user2 = UserModel.builder()
                .name("user2")
                .password("password2")
                .permissions(Set.of("READ", "BID"))
                .isVerified(Boolean.FALSE)
                .email("user2@shopngo.com")
                .build();

        UserModel verifieduser = UserModel.builder()
                .name("verifieduser")
                .password("verifieduserpassword")
                .permissions(Set.of("READ", "BID", "CREATE"))
                .isVerified(Boolean.TRUE)
                .email("verified@shopngo.com")
                .build();

        UserModel admin = UserModel.builder()
                .name("admin")
                .password("admin")
                .permissions(Set.of("READ", "BID", "CREATE", "ADMIN"))
                .isVerified(Boolean.TRUE)
                .email("admin@shopngo.com")
                .build();

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(verifieduser);
        userService.addUser(admin);

        log.info("User data has been populated with user1, user2, verifieduser and admin");
    }

    @GetMapping("/users/getAll")
    @PreAuthorize("@securityService.hasPermission('ADMIN')")
    public List<UserModel> getAllUsers() {
        log.info("Retrieving all existing users");
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/deleteAll")
    @PreAuthorize("@securityService.hasPermission('ADMIN')")
    public void deleteAllUsers() {
        userService.deleteAllUsers();
        log.info("User data has been purged");
    }

    @GetMapping("/items/populate")
    @PreAuthorize("@securityService.hasPermission('ADMIN')")
    public void populateItems() {
        ItemModel item1 = ItemModel.builder()
                .name("Item 1")
                .url("www.shopngo.com/items/item1")
                .originalPrice(BigDecimal.valueOf(10))
                .description("Description for item 1")
                .currency("USD")
                .brand("SuperBrand")
                .build();

        ItemModel item2 = ItemModel.builder()
                .name("Item 2")
                .url("www.shopngo.com/items/item2")
                .originalPrice(BigDecimal.valueOf(5))
                .description("Description for item 2")
                .currency("USD")
                .brand("SuperBrand")
                .build();

        ItemModel item3 = ItemModel.builder()
                .name("Item 3")
                .url("www.shopngo.com/items/item3")
                .originalPrice(BigDecimal.valueOf(3))
                .description("Description for item 3")
                .currency("USD")
                .brand("SuperBrand")
                .build();

        itemService.saveItem(item1);
        itemService.saveItem(item2);
        itemService.saveItem(item3);

        log.info("Item data has been populated with item1, item2 and item3");
    }

    @GetMapping("/items/getAll")
    @PreAuthorize("@securityService.hasPermission('ADMIN')")
    public List<ItemModel> getAllItems() {
        log.info("Retrieving all existing items");
        return itemService.getItems();
    }

    @DeleteMapping("/items/deleteAll")
    @PreAuthorize("@securityService.hasPermission('ADMIN')")
    public void deleteAllItems() {
        itemService.deleteAllItems();
        log.info("Item data has been purged");
    }

    @DeleteMapping("/auctions/deleteAll")
    @PreAuthorize("@securityService.hasPermission('ADMIN')")
    public void deleteAllAuctions() {
        auctionService.deleteAll();
        bidService.deleteAll();
        itemService.deleteAllItems();
        log.info("Auction data has been purged");
    }

    @GetMapping("/auctions/getAll")
    @PreAuthorize("@securityService.hasPermission('ADMIN')")
    public List<AuctionModel> getAllAuctions() {
        log.info("Retrieving all existing auctions");
        return auctionService.getAllAuctions();
    }
}
