package ru.practicum.shareit.booking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOKING_ID_SEQ")
    @SequenceGenerator(name = "BOOKING_ID_SEQ", sequenceName = "BOOKING_ID_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "START_DATE")
    private LocalDateTime start;
    @Column(name = "END_DATE")
    private LocalDateTime end;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ID", nullable = false, updatable = false)
    private Item item;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKER_ID", referencedColumnName = "ID", nullable = false, updatable = false)
    private User booker;
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
