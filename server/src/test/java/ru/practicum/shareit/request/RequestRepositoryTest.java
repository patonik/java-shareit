package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class RequestRepositoryTest {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private User user1;
    private User user2;
    private ItemRequest itemRequest1;
    private ItemRequest itemRequest2;

    @BeforeEach
    void setUp() {
        if (!userRepository.existsById(1L)) {
            user1 = User.builder().id(1L).name("1asd1").email("1asd@asd.ru").build();
            user1 = userRepository.save(user1);
            itemRequest1 = ItemRequest.builder().requester(user1).description("1asd_asd").build();
            itemRequest1 = requestRepository.save(itemRequest1);
            System.out.println(itemRequest1);
        } else {
            user1 = userRepository.findById(1L).orElseThrow();
            itemRequest1 = requestRepository.findById(1L).orElseThrow();
        }
        if (!userRepository.existsById(2L)) {
            user2 = User.builder().id(2L).name("2asd2").email("2asd@asd.ru").build();
            user2 = userRepository.save(user2);
            itemRequest2 = ItemRequest.builder().requester(user2).description("2asd_asd").build();
            itemRequest2 = requestRepository.save(itemRequest2);
            System.out.println(itemRequest2);
        } else {
            user2 = userRepository.findById(2L).orElseThrow();
            itemRequest2 = requestRepository.findById(2L).orElseThrow();
        }
    }

    @Test
    void findAllByRequesterId() {
        ItemRequest first = requestRepository.findAllByRequesterIdOrderByCreatedDesc(user1.getId()).getFirst();
        assertEquals(itemRequest1.getDescription(), first.getDescription());
        assertEquals(itemRequest1.getCreated(), first.getCreated());
    }

    @Test
    void findAllByRequesterIdNot() {
        ItemRequest first = requestRepository.findAllByRequesterIdNotOrderByCreatedDesc(user1.getId()).getFirst();
        assertEquals(itemRequest2.getId(), first.getId());
        assertEquals(itemRequest2.getDescription(), first.getDescription());
        assertEquals(itemRequest2.getCreated(), first.getCreated());
    }
}