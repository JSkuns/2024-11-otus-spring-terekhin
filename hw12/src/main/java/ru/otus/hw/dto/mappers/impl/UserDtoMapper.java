package ru.otus.hw.dto.mappers.impl;

import ru.otus.hw.dto.mappers.DtoMapper;
import ru.otus.hw.dto.models.user.UserDto;
import ru.otus.hw.models.User;

public class UserDtoMapper implements DtoMapper<UserDto, User> {

    @Override
    public UserDto toDto(User source) {
        return new UserDto(source.getId(), source.getUsername(), source.getPassword());
    }

    @Override
    public User toModel(UserDto source) {
        return null;
    }

}
