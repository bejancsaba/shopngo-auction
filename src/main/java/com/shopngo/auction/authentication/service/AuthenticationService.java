package com.shopngo.auction.authentication.service;

import java.util.Optional;

public interface AuthenticationService {
    public Optional<String> authenticate(String userName, String password);
}
