package com.shopngo.auction.portal.controller;

import com.shopngo.auction.portal.domain.AuctionCreationRequest;
import com.shopngo.auction.service.AuctionConverterService;
import com.shopngo.auction.service.AuctionService;
import com.shopngo.auction.service.domain.AuctionModel;
import com.shopngo.auction.user.domain.UserModel;
import com.shopngo.auction.user.serice.UserService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.shopngo.auction.config.WebSocketConfig.TOPIC_DESTINATION_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuctionController {

    private static final String AUCTIONS_TOPIC = TOPIC_DESTINATION_PREFIX + "auctions";

    private final AuctionService auctionService;
    private final AuctionConverterService auctionConverterService;
    private final UserService userService;

    private final SimpMessagingTemplate template;

    @PostMapping("/auction")
    @CrossOrigin(origins = "http://localhost:4200")
    @PreAuthorize("@securityService.hasPermission('CREATE')")
    public void createAuction(@ApiParam(value = "The creation request for auction.", required = true)
                             @RequestBody AuctionCreationRequest auctionRequest, HttpServletResponse response) {
        log.info("Auction creation attempt for user {}", auctionRequest.getOwner());
        String ownerId = userService.getUserByName(auctionRequest.getOwner()).map(UserModel::getId).orElse("N/A");
        auctionService.createAuction(auctionConverterService.convert(auctionRequest, ownerId));
    }

    @GetMapping("/auction/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    @PreAuthorize("@securityService.hasPermission('READ')")
    public AuctionModel getAuction(@ApiParam(value = "The id to look up.", required = true) @PathVariable String id) {
        log.info("Auction lookup for id {}", id);
        return auctionService.getAuction(id).orElseGet(() -> AuctionModel.builder().build());
    }

    @Scheduled(fixedRate = 3000)
    public void shareTime() {
        template.convertAndSend(AUCTIONS_TOPIC, auctionService.getAllAuctions());
    }
}
