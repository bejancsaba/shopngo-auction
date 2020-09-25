package com.shopngo.auction.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class UserModel {
    private final String id;
    private final String name;
    private final String password;
    private final Boolean isVerified;
    private final Set<String> permissions;
}
