package ru.practicum.shareit.item.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.Objects;


/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(schema = "public", name = "ITEM")
@Getter
@Setter
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID", nullable = false, updatable = false)
    private User user;
    private Boolean available;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUEST_ID", referencedColumnName = "ID")
    private ItemRequest itemRequest;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", available=" + available +
            '}';
    }
}
