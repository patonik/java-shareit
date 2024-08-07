package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.DataOperationException;
import ru.practicum.shareit.exception.MissingValueException;
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
        User created;
        try {
            created = userRepository.save(userMapper.toEntity(userDto));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage());
        }
        return userMapper.toDto(created);
    }

    @Override
    public UserDto editUser(UserDto userDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(ConflictException::new);
        String email = userDto.getEmail();
        if (email != null && !email.equals(user.getEmail())) {
            if (userRepository.findByEmail(email) != null) {
                throw new ConflictException();
            }
        }
        User updated = userRepository.save(userMapper.updateEntity(userDto, user));
        return userMapper.toDto(updated);
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto deleteUser(Long userId) {
        User deleted = userRepository.findById(userId).orElseThrow(DataOperationException::new);
        userRepository.deleteById(userId);
        return userMapper.toDto(deleted);
    }
}
