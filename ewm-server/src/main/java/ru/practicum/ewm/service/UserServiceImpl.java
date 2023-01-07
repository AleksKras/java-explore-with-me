package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.mapper.PageMapper;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.dto.UserDto;
import ru.practicum.ewm.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Validated
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserDto create(UserDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toUser(userDto)));
    }

    @Override
    public List<UserDto> getAll(Long[] users, int from, int size) {
        List<Long> ids = List.of(users);

        Page<User> usersPage = userRepository.findAllByIdIn(ids, PageMapper.getPagable(from, size));

        List<User> userList = new ArrayList<>();
        if (usersPage != null && usersPage.hasContent()) {
            userList = usersPage.getContent();
        }
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(userMapper.toDto(user));
        }

        return userDtoList;
    }

    @Override
    public UserDto getById(long userId){
        return userMapper.toDto(userRepository.getReferenceById(userId));
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public void delete(long userId) {
        User user = userRepository.getReferenceById(userId);
        userRepository.delete(user);
    }
}
