package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Repository
public class UserRepository {
    private final Map<String, User> userStorage = new HashMap<>();
    private final Map<Long, String> userEmailIndex = new HashMap<>();
    private long generatedId = 1;

    public boolean containsEmail(String email) {
        return userStorage.containsKey(email);
    }

    public boolean containsUser(Long userId) {
        return userEmailIndex.containsKey(userId);
    }

    public Optional<User> save(User user) {
        String userEmail = user.getEmail();
        Long userId = user.getId();
        if (userId == null) {
            user.setId(generatedId++);
        } else {
            userStorage.remove(userEmailIndex.get(userId));
        }
        userStorage.put(userEmail, user);
        userEmailIndex.put(user.getId(), userEmail);
        return Optional.of(user);
    }

    public Optional<User> getUser(Long userId) {
        return Optional.of(userStorage.get(userEmailIndex.get(userId)));
    }

    public List<User> getUsers() {
        return new ArrayList<>(userStorage.values());
    }

    public Optional<User> deleteUser(Long userId) {
        String email = userEmailIndex.get(userId);
        Optional<User> deleted = Optional.of(userStorage.get(email));
        userStorage.remove(email);
        userEmailIndex.remove(userId);
        return deleted;
    }
}
