package com.shopngo.auction.portal.controller;

import com.shopngo.auction.authentication.domain.EnhancedUserDetails;
import com.shopngo.auction.portal.domain.AuctionCreationRequest;
import com.shopngo.auction.portal.domain.BidRequest;
import com.shopngo.auction.portal.security.SecurityService;
import com.shopngo.auction.service.AuctionConverterService;
import com.shopngo.auction.service.AuctionService;
import com.shopngo.auction.service.domain.AuctionModel;
import com.shopngo.auction.service.domain.BidModel;
import com.shopngo.auction.user.domain.UserModel;
import com.shopngo.auction.user.serice.UserService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static com.shopngo.auction.config.WebSocketConfig.TOPIC_DESTINATION_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuctionController {

    private static final String AUCTION_TOPIC_PREFIX = TOPIC_DESTINATION_PREFIX + "auction/";
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
        // TODO validate against security context
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

    @PostMapping("/auction/{id}/bid")
    @CrossOrigin(origins = "http://localhost:4200")
    @PreAuthorize("@securityService.hasPermission('BID')")
    public void placeBid(@ApiParam(value = "The auction id.", required = true) @PathVariable String id,
                         @ApiParam(value = "The request for placing a  bid.", required = true)
                          @RequestBody BidRequest bidRequest) {
        String bidderName = SecurityService.getEnhancedUserDetails().map(EnhancedUserDetails::getUsername).orElse("N/A");
        String bidderId = userService.getUserByName(bidderName).map(UserModel::getId).orElse("N/A");
        log.info("Bid creation attempt for user {} with id {}", bidderName, bidderId);

        auctionService.placeBid(BidModel.builder()
                .timestamp(Instant.now().toEpochMilli())
                .bid(new BigDecimal(bidRequest.getBid()))
                .auctionId(id)
                .bidder(bidderId)
                .build()
        );
    }

    @Scheduled(fixedRate = 3000)
    public void updateLanding() {
        template.convertAndSend(AUCTIONS_TOPIC, auctionService.getAllAuctions());
    }

    @Scheduled(fixedRate = 1000)
    public void updateAuctions() {
        Map<String, List<BidModel>> auctions = auctionService.getAllAuctionsWithBids();
        auctions.keySet().forEach(
            auctionId -> template.convertAndSend(AUCTION_TOPIC_PREFIX + auctionId, auctions.get(auctionId))
        );
    }
}
