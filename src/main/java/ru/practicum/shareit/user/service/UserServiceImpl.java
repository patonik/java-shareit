package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.exception.ConflictException;
import ru.practicum.shareit.user.exception.DataOperationException;
import ru.practicum.shareit.user.exception.MissingValueException;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto addUser(UserDto userDto) {
        String email = userDto.getEmail();
        if (email == null) {
            throw new MissingValueException();
        }
        if (userRepository.containsEmail(email)) {
            throw new ConflictException();
        }
        User created = userRepository.save(userMapper.toEntity(userDto));
        if (created == null) {
            throw new DataOperationException();
        }
        return userMapper.toDto(created);
    }

    @Override
    public UserDto editUser(UserDto userDto, Long userId) {
        if (!userRepository.containsUser(userId)) {
            throw new ConflictException();
        }
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new DataOperationException();
        }
        String email = userDto.getEmail();
        if (email != null && !email.equals(user.getEmail())) {
            if (userRepository.containsEmail(email)) {
                throw new ConflictException();
            }
        }
        User updated = userRepository.save(userMapper.updateEntity(userDto, user));
        if (updated == null) {
            throw new ConflictException();
        }
        return userMapper.toDto(updated);
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new DataOperationException();
        }
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.getUsers().stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto deleteUser(Long userId) {
        User deleted = userRepository.deleteUser(userId);
        if (deleted == null) {
            throw new DataOperationException();
        }
        return userMapper.toDto(deleted);
    }
}
