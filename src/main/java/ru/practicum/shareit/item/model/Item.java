package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(schema = "public", name = "ITEM")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_ID_SEQ")
    @SequenceGenerator(name = "ITEM_ID_SEQ", sequenceName = "ITEM_ID_SEQ", allocationSize = 1)
    private Long id;
    private String name;
    private String description;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "OWNER_ID", nullable = false, updatable = false)
    private User user;
    private Boolean available;
}
