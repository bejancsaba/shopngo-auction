package com.shopngo.auction.service;

import com.shopngo.auction.service.dao.entity.AuctionEntity;
import com.shopngo.auction.service.domain.AuctionModel;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.EMPTY;

public class AuctionConverterService {

    private static final String SERIALIZED_LIST_DELIMITER = ",";

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
                .ownerId(entity.getOwnerId())
                .startingBid(entity.getStartingBid())
                .tags(stringToList(entity.getTags()))
                .targetCountries(stringToList(entity.getTargetCountries()))
                .type(entity.getType())
                .build();
    }

    private String listToString(List<String> list) {
        return CollectionUtils.isEmpty(list) ? EMPTY : String.join(SERIALIZED_LIST_DELIMITER, list);
    }

    private List<String> stringToList(String data) {
        return StringUtils.isEmpty(data) ? List.of(EMPTY) : List.of(data.split(SERIALIZED_LIST_DELIMITER));
    }
}
