package ru.practicum.shareit.request.model;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NamedNativeQuery;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "request")
@NamedNativeQuery(
    name = "request_query_dto",
    query = "SELECT r.id AS request_id, " +
        "r.description AS request_description, " +
        "r.created, " +
        "i.id AS item_id, " +
        "i.name AS item_name, " +
        "i.description AS item_description, " +
        "i.available AS item_available " +
        "FROM request r LEFT JOIN request_item ri ON r.id = ri.request_id LEFT JOIN item i ON ri.item_id = i.id " +
        "WHERE r.requester_id = :requesterId ",
    resultSetMapping = "request_query_dto"
)
@SqlResultSetMapping(
    name = "request_query_dto",
    classes = {@ConstructorResult(
        targetClass = ItemRequestDto.class,
        columns = {
            @ColumnResult(name = "id", type = Long.class),
            @ColumnResult(name = "description", type = String.class),
            @ColumnResult(name = "created", type = LocalDateTime.class),
        }
    ),
        @ConstructorResult(targetClass = Item.class,
            columns = {
                @ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "name", type = String.class),
                @ColumnResult(name = "description", type = String.class),
                @ColumnResult(name = "available", type = Boolean.class),
            })}
)
@NamedNativeQuery(
    name = "request_query_dto_not",
    query = "SELECT r.id AS request_id, " +
        "r.description AS request_description, " +
        "r.created, " +
        "i.id AS item_id, " +
        "i.name AS item_name, " +
        "i.description AS item_description, " +
        "i.available AS item_available " +
        "FROM request r LEFT JOIN request_item ri ON r.id = ri.request_id LEFT JOIN item i ON ri.item_id = i.id " +
        "WHERE r.requester_id <> :requesterId ",
    resultSetMapping = "request_query_dto_not"
)
@SqlResultSetMapping(
    name = "request_query_dto_not",
    classes = {@ConstructorResult(
        targetClass = ItemRequestDto.class,
        columns = {
            @ColumnResult(name = "id", type = Long.class),
            @ColumnResult(name = "description", type = String.class),
            @ColumnResult(name = "created", type = LocalDateTime.class),
        }
    ),
        @ConstructorResult(targetClass = Item.class,
            columns = {
                @ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "name", type = String.class),
                @ColumnResult(name = "description", type = String.class),
                @ColumnResult(name = "available", type = Boolean.class),
            })}
)
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
    @ManyToMany
    @JoinTable(name = "request_item",
        joinColumns = @JoinColumn(name = "request_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items;
}
