package ru.practicum.ewm.service;

import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    List<UserDto> getAll(Long[] users, int from, int size);

    UserDto getById(long id);

    User getUserById(long id);

    void delete(long userId);
}
