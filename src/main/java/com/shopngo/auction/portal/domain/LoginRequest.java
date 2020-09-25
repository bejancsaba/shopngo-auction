package com.shopngo.auction.portal.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = "password")
@AllArgsConstructor
@ApiModel(value = "LoginRequest", description = "The Login Request.")
public class LoginRequest {

    @ApiModelProperty(value = "The user name", example = "exampleUserName", required = true)
    private final String username;

    @ApiModelProperty(value = "The password", example = "examplePassword", required = true)
    private final String password;
}
