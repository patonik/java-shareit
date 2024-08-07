package ru.practicum.shareit.item.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.shareit.user.model.User;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_ID_SEQ")
    @SequenceGenerator(name = "COMMENT_ID_SEQ", sequenceName = "COMMENT_ID_SEQ", allocationSize = 1)
    private Long id;
    private String text;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ID", nullable = false, updatable = false)
    private Item item;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID", nullable = false, updatable = false)
    private User author;
    @CreationTimestamp
    private LocalDateTime created;
}
