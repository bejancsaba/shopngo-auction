package com.shopngo.auction.portal.security;


import com.shopngo.auction.authentication.domain.EnhancedUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

@Slf4j
public class SecurityService {

    public boolean hasPermission(String permission) {
        return getPermissions().contains(permission.toUpperCase());
    }

    private static List<String> getPermissions() {
        return getEnhancedUserDetails()
                .map(enhancedUserDetails -> enhancedUserDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(toList()))
                .orElse(List.of());
    }

    private static Optional<EnhancedUserDetails> getEnhancedUserDetails() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(not((AnonymousAuthenticationToken.class::isInstance)))
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(EnhancedUserDetails.class::cast);
    }
}
