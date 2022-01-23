package com.spinnerapp.mapper;

import com.spinnerapp.model.dto.UserRequestDto;
import com.spinnerapp.model.dto.UserResponseDto;
import com.spinnerapp.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    default List<UserResponseDto> entitiesToDto(List<User> users) {
        List<UserResponseDto> dtos = new ArrayList<>();
        users.forEach(user -> dtos.add(entityToDto(user)));
        return dtos;
    }

    UserResponseDto entityToDto(User user);


    User dtoToEntity(UserRequestDto userRequestDto);

}