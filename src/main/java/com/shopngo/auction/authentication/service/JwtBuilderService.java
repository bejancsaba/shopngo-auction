package com.shopngo.auction.authentication.service;

import com.auth0.jwt.JWT;
import com.shopngo.auction.user.domain.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.shopngo.auction.config.SecurityConfig.*;

@Slf4j
public class JwtBuilderService {

    private static final String SERIALIZED_LIST_DELIMITER = ",";

    @Value("${security.jwt.token-validity-in-seconds}")
    private long tokenValidity;

    public Optional<String> createToken(UserModel model) {
        Optional<String> token = Optional.empty();
        try {
            String loginSession = UUID.randomUUID().toString();

            token = Optional.of(
                    JWT.create()
                        .withClaim(AUTH_KEY_NAME, String.join(SERIALIZED_LIST_DELIMITER, model.getPermissions()))
                        .withClaim(LOGIN_SESSION_KEY_NAME, loginSession)
                        .withClaim(EMAIL_KEY_NAME, model.getEmail())
                        .withClaim(VERIFIED_USER_KEY_NAME, model.getIsVerified())
                        .withSubject(model.getName())
                        .withIssuer(JWT_ISSUER)
                        .withExpiresAt(determineValidity())
                        .sign(ALGORITHM)
            );
        } catch (com.auth0.jwt.exceptions.JWTCreationException exception) {
            log.error("JWT creation issue: " + exception);
        }

        return token;
    }

    private Date determineValidity() {
        return calculateValidityTimeStamp(tokenValidity);
    }

    private Date calculateValidityTimeStamp(long validityInSeconds) {
        return new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(validityInSeconds));
    }
}
