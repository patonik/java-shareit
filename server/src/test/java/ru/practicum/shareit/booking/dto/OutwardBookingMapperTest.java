package ru.practicum.shareit.booking.dto;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
class OutwardBookingMapperTest {
    private final OutwardBookingMapper outwardBookingMapper;

    @Test
    void toDto() {
        User user = User.builder().id(1L).name("asd").email("asd@asd.ru").build();
        Item item = Item.builder().id(1L).name("asd").user(user).available(true).description("asd").build();
        Booking booking =
            Booking.builder().id(1L).start(LocalDateTime.MIN).end(LocalDateTime.MAX).booker(user).item(item).status(
                Status.WAITING).build();
        OutwardBookingDto outwardBookingDto =
            outwardBookingMapper.toDto(booking);
        System.out.println("createBookingDto = " + outwardBookingDto);
    }
}