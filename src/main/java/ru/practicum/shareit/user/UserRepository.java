package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final Map<Long, User> userStorage = new HashMap<>();
    private long generatedId = 1;

    public boolean containsUser(Long userId) {
        return userStorage.containsKey(userId);
    }

    public Optional<User> save(User user) {
        if (user.getId() == null){
            user.setId(generatedId++);
        } else if (!userStorage.containsKey(user.getId())) {
            return Optional.empty();
        }
        userStorage.put(user.getId(), user);
        return Optional.of(user);
    }
}
