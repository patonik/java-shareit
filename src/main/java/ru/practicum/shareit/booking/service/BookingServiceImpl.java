package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.controller.State;
import ru.practicum.shareit.booking.dto.InwardBookingDto;
import ru.practicum.shareit.booking.dto.InwardBookingMapper;
import ru.practicum.shareit.booking.dto.OutwardBookingDto;
import ru.practicum.shareit.booking.dto.OutwardBookingMapper;
import ru.practicum.shareit.booking.exception.NotAvailableException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.DataOperationException;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.exception.MissingValueException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OutwardBookingMapper outwardBookingMapper;
    private final InwardBookingMapper inwardBookingMapper;

    public OutwardBookingDto addBooking(InwardBookingDto inwardBookingDto, Long userId) {
        LocalDateTime start = inwardBookingDto.getStart();
        LocalDateTime end = inwardBookingDto.getEnd();
        if (start == null ||
            end == null) {
            throw new MissingValueException();
        }
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Item item = itemRepository.findById(inwardBookingDto.getItemId()).orElseThrow(NotFoundException::new);
        if (userId.equals(item.getUser().getId())) {
            throw new EntityNotFoundException();
        }
        if (!item.getAvailable()) {
            throw new NotAvailableException();
        }
        Booking created =
            bookingRepository.save(inwardBookingMapper.toEntity(inwardBookingDto, item, user));
        log.info("entity created: {}", created);
        return outwardBookingMapper.toDto(created);
    }

    @Override
    public OutwardBookingDto approveBooking(Long bookingId, Long userId, Boolean approved) {
        User user = userRepository.findById(userId).orElseThrow(AccessException::new);
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
        if (!user.equals(booking.getItem().getUser())) {
            throw new AccessException();
        }
        if (approved && !booking.getStatus().equals(Status.APPROVED)) {
            booking.setStatus(Status.APPROVED);
        } else if (!approved && !booking.getStatus().equals(Status.REJECTED)) {
            booking.setStatus(Status.REJECTED);
        } else {
            throw new DataOperationException();
        }
        booking = bookingRepository.save(booking);
        return outwardBookingMapper.toDto(booking);
    }

    @Override
    public OutwardBookingDto getBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
        if (!userId.equals(booking.getBooker().getId())
            && !userId.equals(booking.getItem().getUser().getId())) {
            throw new AccessException();
        }
        return outwardBookingMapper.toDto(booking);
    }

    @Override
    public List<OutwardBookingDto> getUserBookings(Long userId, State state) {
        return switch (state) {
            case ALL -> bookingRepository.findAllByUserId(userId);
            case REJECTED -> bookingRepository.findAllByUserIdAndStatus(userId, Status.REJECTED);
            case WAITING -> bookingRepository.findAllByUserIdAndStatus(userId, Status.WAITING);
            case CURRENT -> bookingRepository.findAllByUserIdCurrent(userId);
            case FUTURE -> bookingRepository.findAllByUserIdFuture(userId);
            case PAST -> bookingRepository.findAllByUserIdPast(userId);
        };
    }

    @Override
    public List<OutwardBookingDto> getOwnerBookings(Long userId, State state) {
        userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return switch (state) {
            case ALL -> bookingRepository.findAllByOwnerId(userId);
            case REJECTED -> bookingRepository.findAllByOwnerIdAndStatus(userId, Status.REJECTED);
            case WAITING -> bookingRepository.findAllByOwnerIdAndStatus(userId, Status.WAITING);
            case CURRENT -> bookingRepository.findAllByOwnerIdCurrent(userId);
            case FUTURE -> bookingRepository.findAllByOwnerIdFuture(userId);
            case PAST -> bookingRepository.findAllByOwnerIdPast(userId);
        };
    }
}
