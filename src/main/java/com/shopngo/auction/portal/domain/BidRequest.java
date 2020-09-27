package com.shopngo.auction.portal.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "BidRequest", description = "Represents oe bid event.")
public class BidRequest {

    @ApiModelProperty(value = "The value of the bid made", example = "13.5", required = true)
    private String bid;
}
