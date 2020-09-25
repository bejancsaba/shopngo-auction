package com.shopngo.auction.user.dao.repository;

import com.shopngo.auction.user.dao.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, String> {

    Optional<UserEntity> findUserEntityByName(String name);
}
