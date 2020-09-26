package com.shopngo.auction.portal.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel(value = "AuctionCreationRequest", description = "Request representing the auction creation event.")
public class AuctionCreationRequest {

    @ApiModelProperty(value = "Type of the auction", example = "Standard", required = true)
    private final String type;

    @ApiModelProperty(value = "User name of the auction owner", example = "bcsaba", required = true)
    private final String owner;

    @ApiModelProperty(value = "The id of the item under auction", example = "123456", required = true)
    private final String itemId;

    @ApiModelProperty(value = "Description for the auction", example = "This is my favourite lamp I want to sell.")
    private final String description;

    @ApiModelProperty(value = "The countries where this auction should be live.", example = "Hungary", required = true)
    private final String targetCountries;

    @ApiModelProperty(value = "Tags to support searching for auctions.", example = "electronic,garden,outdoor")
    private final String tags;

    @ApiModelProperty(value = "The initial value for bidding. By default in USD.", example = "350", required = true)
    private final String startingBid;

    @ApiModelProperty(value = "The date when the auction should go live in epoch.", example = "1601144573", required = true)
    private final long startDate;

    @ApiModelProperty(value = "The date when the auction should end in epoch.", example = "1601144574", required = true)
    private final long endDate;
}
