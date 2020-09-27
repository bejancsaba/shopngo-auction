package com.shopngo.auction.portal.controller;

import com.shopngo.auction.authentication.service.AuthenticationService;
import com.shopngo.auction.portal.domain.LoginRequest;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.shopngo.auction.config.SecurityConfig.BEARER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public void attemptLogin(@ApiParam(value = "The credentials of the user.", required = true)
                             @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        log.info("Login attempt for user {}", loginRequest.getUsername());
        authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword())
                .ifPresentOrElse(
                    token -> response.addHeader(AUTHORIZATION, BEARER_PREFIX + token),
                    () -> response.setStatus(HttpStatus.BAD_REQUEST.value())
            );
    }
}
