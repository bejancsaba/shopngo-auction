package com.shopngo.auction.portal.controller;

import com.shopngo.auction.user.domain.UserModel;
import com.shopngo.auction.user.serice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * !!! Use only for test purposes to prepopulate data !!!
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Profile("!prod")
public class AdminController {

    private final UserService userService;

    @GetMapping("/users/populate")
    @Secured("ROLE_ADMIN")
    public void populateUsers() {
        UserModel user1 = UserModel.builder()
                .name("user1")
                .password("password1")
                .permissions(Set.of("READ", "BID"))
                .isVerified(Boolean.FALSE)
                .email("user1@shopngo.com")
                .build();

        UserModel user2 = UserModel.builder()
                .name("user2")
                .password("password2")
                .permissions(Set.of("READ", "BID"))
                .isVerified(Boolean.FALSE)
                .email("user2@shopngo.com")
                .build();

        UserModel verifieduser = UserModel.builder()
                .name("verifieduser")
                .password("verifieduserpassword")
                .permissions(Set.of("READ", "BID", "CREATE"))
                .isVerified(Boolean.TRUE)
                .email("verified@shopngo.com")
                .build();

        UserModel admin = UserModel.builder()
                .name("admin")
                .password("admin")
                .permissions(Set.of("READ", "BID", "CREATE"))
                .isVerified(Boolean.TRUE)
                .email("admin@shopngo.com")
                .build();

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(verifieduser);
        userService.addUser(admin);

        log.info("User data has been populated with user1, user2, verifieduser and admin");
    }

    @GetMapping("/users/getAll")
    @Secured("ROLE_ADMIN")
    public List<UserModel> getAllUsers() {
        log.info("Retrieving all existing users");
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/deleteAll")
    @Secured("ROLE_ADMIN")
    public void deleteAllUsers() {
        userService.deleteAllUsers();
        log.info("User data has been purged");
    }
}
