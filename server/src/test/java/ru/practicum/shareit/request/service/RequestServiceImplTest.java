package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
    "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true",
    "spring.jackson.serialization.fail-on-empty-beans=false"
})
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class RequestServiceImplTest {
    private final RequestService requestService;
    private final UserServiceImpl userService;
    private final ItemServiceImpl itemService;
    private UserDto user;
    private ItemRequestDto initialDto;
    private ItemDto initialItem;

    @BeforeEach
    void setUp() {
        UserDto userDto =
            UserDto.builder().name("Mao: " + this.hashCode()).email("hunweibean2@javabeans.com_" + this.hashCode())
                .build();
        initialDto =
            ItemRequestDto.builder().description("nuclear warhead: " + this.hashCode()).build();
        user = userService.addUser(userDto);
        initialDto = requestService.addRequest(user.getId(), initialDto);
        ItemDto itemDto = ItemDto.builder()
            .requestId(initialDto.getId())
            .name("bowl of rice")
            .available(true)
            .description("nuclear warhead: " + this.hashCode()).build();
        initialItem = itemService.addItem(itemDto, user.getId());
    }

    @Test
    void addRequest() {
        initialDto =
            ItemRequestDto.builder().description("nuclear warhead: " + (this.hashCode() + 1)).build();
        initialDto = requestService.addRequest(user.getId(), initialDto);
        ItemDto itemDto = ItemDto.builder()
            .requestId(initialDto.getId())
            .name("bowl of rice")
            .available(true)
            .description("nuclear warhead: " + this.hashCode()).build();
        initialItem = itemService.addItem(itemDto, user.getId());
        List<ItemRequestDto> myRequests = requestService.getMyRequests(user.getId());
        assertTrue(myRequests.contains(initialDto));
        System.out.println(myRequests);
        assertTrue(myRequests.stream().flatMap(x -> x.getItems().stream())
            .anyMatch(x -> x.getId().equals(initialItem.getId())));
    }

    @Test
    void getAllRequests() {
        UserDto userDto =
            UserDto.builder().name("Mao: " + (this.hashCode() + 1))
                .email("hunweibean2@javabeans.com_" + (this.hashCode() + 1))
                .build();
        ItemRequestDto itemRequestDto =
            ItemRequestDto.builder().description("nuclear warhead: " + (this.hashCode() + 1)).build();
        userDto = userService.addUser(userDto);
        itemRequestDto = requestService.addRequest(userDto.getId(), itemRequestDto);
        UserDto finalUserDto = userDto;
        ItemRequestDto finalItemRequestDto = itemRequestDto;
        List<ItemRequestDto> allRequests = requestService.getAllRequests(finalUserDto.getId());
        assertAll(() -> assertFalse(allRequests.contains(finalItemRequestDto)),
            () -> assertTrue(allRequests.contains(initialDto)));
    }

    @Test
    void getRequest() {
        ItemRequestDto itemRequestDto = requestService.getRequest(initialDto.getId());
        assertEquals(initialDto.getId(), itemRequestDto.getId());
        assertEquals(initialDto.getCreated(), itemRequestDto.getCreated());
        assertEquals(initialDto.getDescription(), itemRequestDto.getDescription());
        System.out.println(itemRequestDto.getItems());
        assertTrue(
            itemRequestDto.getItems().stream().mapToLong(ItemDto::getId).anyMatch(x -> x == initialItem.getId()));
    }
}