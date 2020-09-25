package com.shopngo.auction.portal.controller;

import com.shopngo.auction.identity.service.IdentityService;
import com.shopngo.auction.portal.domain.LoginRequest;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final IdentityService identityService;

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public void attemptLogin(@ApiParam(value = "The credentials of the user.", required = true)
            @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        log.info("Login attempt for user {}", loginRequest.getUsername());
        String result = identityService.identify(loginRequest.getUsername(), loginRequest.getPassword());
        log.info("Result - {}", result);
    }
}
