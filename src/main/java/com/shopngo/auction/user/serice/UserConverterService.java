package com.shopngo.auction.user.serice;

import com.shopngo.auction.user.domain.UserModel;
import com.shopngo.auction.user.dao.entity.UserEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

import static org.apache.logging.log4j.util.Strings.EMPTY;

public class UserConverterService {

    private static final String SERIALIZED_LIST_DELIMITER = ",";

    public UserEntity convert(UserModel model) {
        return UserEntity.builder()
                .id(model.getId())
                .isVerified(model.getIsVerified())
                .name(model.getName())
                .password(model.getPassword())
                .email(model.getEmail())
                .country(model.getCountry())
                .permissions(CollectionUtils.isEmpty(model.getPermissions()) ? EMPTY
                        : String.join(SERIALIZED_LIST_DELIMITER, model.getPermissions()))
                .build();
    }

    public UserModel convert(UserEntity entity) {
        return UserModel.builder()
                .id(entity.getId())
                .isVerified(entity.getIsVerified())
                .name(entity.getName())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .country(entity.getCountry())
                .permissions(StringUtils.isEmpty(entity.getPermissions()) ? Set.of(EMPTY) :
                        Set.of(entity.getPermissions().split(SERIALIZED_LIST_DELIMITER)))
                .build();
    }
}
