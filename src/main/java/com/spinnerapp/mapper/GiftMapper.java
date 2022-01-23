package com.spinnerapp.mapper;

import com.spinnerapp.model.dto.GiftRequestDto;
import com.spinnerapp.model.dto.GiftResponseDto;
import com.spinnerapp.model.entity.Gift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface GiftMapper {

    default List<GiftResponseDto> entitiesToDto(List<Gift> gifts) {
        List<GiftResponseDto> dtos = new ArrayList<>();
        gifts.forEach(gift -> dtos.add(entityToDto(gift)));
        return dtos;
    }

    @Mapping(target = "userId", source = "user.id")
    GiftResponseDto entityToDto(Gift gift);


    @Mapping(target = "user", ignore = true)
    Gift dtoToEntity(GiftRequestDto giftRequestDto);

}