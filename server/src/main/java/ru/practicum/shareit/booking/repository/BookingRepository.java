package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.dto.OutwardBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(
        "select new ru.practicum.shareit.booking.dto.OutwardBookingDto(b.id, b.start, b.end, b.item, b.booker, b.status) " +
            "from Booking b join b.item i " +
            "join b.booker bk " +
            "where bk.id = :userId order by b.start desc")
    List<OutwardBookingDto> findAllByUserId(Long userId);

    @Query(
        "select new ru.practicum.shareit.booking.dto.OutwardBookingDto(b.id, b.start, b.end, b.item, b.booker, b.status) " +
            "from Booking b join b.item i " +
            "join b.booker bk " +
            "where bk.id = :userId and b.status = :status order by b.start desc")
    List<OutwardBookingDto> findAllByUserIdAndStatus(Long userId, Status status);

    @Query(
        "select new ru.practicum.shareit.booking.dto.OutwardBookingDto(b.id, b.start, b.end, b.item, b.booker, b.status) " +
            "from Booking b join b.item i " +
            "join b.booker bk " +
            "where bk.id = :userId " +
            "and CURRENT_TIMESTAMP > b.start and CURRENT_TIMESTAMP < b.end order by b.start desc")
    List<OutwardBookingDto> findAllByUserIdCurrent(Long userId);

    @Query(
        "select new ru.practicum.shareit.booking.dto.OutwardBookingDto(b.id, b.start, b.end, b.item, b.booker, b.status) " +
            "from Booking b join b.item i " +
            "join b.booker bk " +
            "where bk.id = :userId and CURRENT_TIMESTAMP < b.start order by b.start desc")
    List<OutwardBookingDto> findAllByUserIdFuture(Long userId);

    @Query(
        "select new ru.practicum.shareit.booking.dto.OutwardBookingDto(b.id, b.start, b.end, b.item, b.booker, b.status) " +
            "from Booking b join b.item i " +
            "join b.booker bk " +
            "where bk.id = :userId and CURRENT_TIMESTAMP > b.end order by b.start desc")
    List<OutwardBookingDto> findAllByUserIdPast(Long userId);

    @Query(
        "select new ru.practicum.shareit.booking.dto.OutwardBookingDto(b.id, b.start, b.end, b.item, b.booker, b.status) " +
            "from Booking b " +
            "join b.item i join i.user u " +
            "where u.id = :userId order by b.start desc")
    List<OutwardBookingDto> findAllByOwnerId(Long userId);

    @Query(
        "select new ru.practicum.shareit.booking.dto.OutwardBookingDto(b.id, b.start, b.end, b.item, b.booker, b.status) " +
            "from Booking b " +
            "join b.item i join i.user u " +
            "where u.id = :userId and b.status = :status order by b.start desc")
    List<OutwardBookingDto> findAllByOwnerIdAndStatus(Long userId, Status status);

    @Query(
        "select new ru.practicum.shareit.booking.dto.OutwardBookingDto(b.id, b.start, b.end, b.item, b.booker, b.status) " +
            "from Booking b " +
            "join b.item i join i.user u " +
            "where u.id = :userId " +
            "and CURRENT_TIMESTAMP > b.start and CURRENT_TIMESTAMP < b.end order by b.start desc")
    List<OutwardBookingDto> findAllByOwnerIdCurrent(Long userId);

    @Query(
        "select new ru.practicum.shareit.booking.dto.OutwardBookingDto(b.id, b.start, b.end, b.item, b.booker, b.status) " +
            "from Booking b " +
            "join b.item i join i.user u " +
            "where u.id = :userId " +
            "and CURRENT_TIMESTAMP < b.start order by b.start desc")
    List<OutwardBookingDto> findAllByOwnerIdFuture(Long userId);

    @Query(
        "select new ru.practicum.shareit.booking.dto.OutwardBookingDto(b.id, b.start, b.end, b.item, b.booker, b.status) " +
            "from Booking b " +
            "join b.item i join i.user u " +
            "where u.id = :userId " +
            "and CURRENT_TIMESTAMP > b.end order by b.start desc")
    List<OutwardBookingDto> findAllByOwnerIdPast(Long userId);

    Booking findFirstByItemIdAndEndBeforeOrderByEndDesc(Long itemId, LocalDateTime currentDateTime);

    Booking findFirstByItemIdAndStartAfterOrderByStartAsc(Long itemId, LocalDateTime currentDateTime);

    Boolean existsByItemIdAndBookerIdAndEndIsLessThanEqual(Long itemId, Long userId, LocalDateTime now);
}
