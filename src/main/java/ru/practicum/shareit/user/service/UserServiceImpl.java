package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.exception.ConflictException;
import ru.practicum.shareit.user.exception.RepositoryException;
import ru.practicum.shareit.user.model.User;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        if (userRepository.containsUser(user.getId())) {
            throw new ConflictException();
        }
        user.setId(null);
        return userRepository.save(user).orElseThrow(RepositoryException::new);
    }
}
