package com.shopngo.auction.identity.service;

import com.shopngo.auction.user.serice.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultIdentityService implements IdentityService {

    private final UserService userService;

    @Override
    public String identify(String userName, String password) {
        return userService.getUserByName(userName)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> "SUCCESS Login for: " + user.getName())
                .orElse("UNSUCCESSFUL Login attempt for: " + userName);
    }
}
