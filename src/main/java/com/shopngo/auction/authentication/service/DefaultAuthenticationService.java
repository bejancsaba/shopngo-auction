package com.shopngo.auction.authentication.service;

import com.shopngo.auction.user.serice.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {

    private final UserService userService;
    private final JwtBuilderService jwtBuilderService;

    @Override
    public Optional<String> authenticate(String userName, String password) {
        return userService.getUserByName(userName)
                .filter(user -> user.getPassword().equals(password))
                .flatMap(jwtBuilderService::createToken);
    }
}
