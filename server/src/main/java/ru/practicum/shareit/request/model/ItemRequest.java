package ru.practicum.shareit.request.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "request")
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REQ_ID_SEQ")
    @SequenceGenerator(name = "REQ_ID_SEQ", sequenceName = "REQ_ID_SEQ", allocationSize = 1)
    private Long id;
    private String description;
    @OneToOne
    private User requester;
    @CreationTimestamp
    private LocalDateTime created;
    @OneToMany(mappedBy = "itemRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Item> items = new HashSet<>();

    public void addItem(Item item) {
        items.add(item);
        item.setItemRequest(this);
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.setItemRequest(null);
    }
}
