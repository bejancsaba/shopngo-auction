package com.shopngo.auction.service;

import com.shopngo.auction.item.domain.ItemModel;
import com.shopngo.auction.item.service.ItemService;
import com.shopngo.auction.portal.domain.AuctionCreationRequest;
import com.shopngo.auction.service.dao.entity.AuctionEntity;
import com.shopngo.auction.service.domain.AuctionModel;
import com.shopngo.auction.user.domain.UserModel;
import com.shopngo.auction.user.serice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@RequiredArgsConstructor
public class AuctionConverterService {

    private static final String SERIALIZED_LIST_DELIMITER = ",";

    private final UserService userService;
    private final ItemService itemService;

    public AuctionEntity convert(AuctionModel model) {
        return AuctionEntity.builder()
                .id(model.getId())
                .description(model.getDescription())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .itemId(model.getItemId())
                .ownerId(model.getOwnerId())
                .startingBid(model.getStartingBid())
                .tags(listToString(model.getTags()))
                .targetCountries(listToString(model.getTargetCountries()))
                .type(model.getType())
                .build();
    }

    public AuctionModel convert(AuctionEntity entity) {
        return AuctionModel.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .itemId(entity.getItemId())
                .itemName(itemService.getItem(entity.getItemId()).map(ItemModel::getName).orElse("N/A"))
                .ownerId(entity.getOwnerId())
                .ownerName(userService.getUserById(entity.getOwnerId()).map(UserModel::getName).orElse("N/A"))
                .startingBid(entity.getStartingBid())
                .tags(stringToList(entity.getTags()))
                .targetCountries(stringToList(entity.getTargetCountries()))
                .type(entity.getType())
                .build();
    }

    public AuctionModel convert(AuctionCreationRequest request, String ownerId) {
        return AuctionModel.builder()
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .itemId(request.getItemId())
                .ownerId(ownerId)
                .ownerName(request.getOwner())
                .startingBid(new BigDecimal(request.getStartingBid()))
                .tags(stringToList(request.getTags()))
                .targetCountries(stringToList(request.getTargetCountries()))
                .type(request.getType())
                .build();
    }

    private String listToString(List<String> list) {
        return CollectionUtils.isEmpty(list) ? EMPTY : String.join(SERIALIZED_LIST_DELIMITER, list);
    }

    private List<String> stringToList(String data) {
        return StringUtils.isEmpty(data) ? List.of(EMPTY) : List.of(data.split(SERIALIZED_LIST_DELIMITER));
    }
}
