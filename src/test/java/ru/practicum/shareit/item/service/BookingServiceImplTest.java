package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BookingServiceImplTest {
    @Autowired
    ItemServiceImpl itemService;
    @Autowired
    UserServiceImpl userService;
    private ItemDto initialItemDto;

    @BeforeEach
    void setUp() {
        UserDto userDto = UserDto.builder().name("Mao").email("hunweibean2@javabeans.com").build();
        initialItemDto =
            ItemDto.builder().name("nuclear warhead").description("伟大舵手领导的伟大革命").available(true).build();
        userService.addUser(userDto);
        initialItemDto = itemService.addItem(initialItemDto, 1L);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void editItem() {
        ItemDto item = itemService.getItem(initialItemDto.getId(), 1L);
        assertAll(() -> assertEquals(initialItemDto.getDescription(), item.getDescription()),
            () -> assertEquals(initialItemDto.getAvailable(), item.getAvailable()),
            () -> assertEquals(initialItemDto.getName(), item.getName()),
            () -> assertNotNull(item.getId())
        );
        System.out.println(item);
        ItemDto itemDto =
            ItemDto.builder().id(1L).name("bowl of rice").description("伟大舵手领导的伟大革命").available(false)
                .build();
        ItemDto actual = itemService.editItem(itemDto, 1L, 1L);
        actual.setUser(null);
        assertEquals(itemDto, actual);
    }
}