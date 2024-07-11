package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.exception.ConflictException;
import ru.practicum.shareit.user.exception.DataOperationException;
import ru.practicum.shareit.user.exception.MissingValueException;
import ru.practicum.shareit.user.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) {
        String email = userDto.getEmailIfExists().orElseThrow(MissingValueException::new);
        if (userRepository.containsEmail(email)) {
            throw new ConflictException();
        }
        User created = userRepository.save(UserMapper.toEntity(userDto)).orElseThrow(DataOperationException::new);
        return UserMapper.toDto(created);
    }

    @Override
    public UserDto editUser(UserDto userDto, Long userId) {
        if (!userRepository.containsUser(userId)) {
            throw new ConflictException();
        }
        User user = userRepository.getUser(userId).orElseThrow(DataOperationException::new);
        String email = userDto.getEmail();
        if (email != null && !email.equals(user.getEmail())) {
            if (userRepository.containsEmail(email)) {
                throw new ConflictException();
            }
        }
        User updated = userRepository.save(UserMapper.toEntity(userDto, user)).orElseThrow(ConflictException::new);
        return UserMapper.toDto(updated);
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = userRepository.getUser(userId).orElseThrow(DataOperationException::new);
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.getUsers().stream().map(UserMapper::toDto).toList();
    }

    @Override
    public UserDto deleteUser(Long userId) {
        User deleted = userRepository.deleteUser(userId).orElseThrow(NotFoundException::new);
        return UserMapper.toDto(deleted);
    }
}
