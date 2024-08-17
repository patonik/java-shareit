package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.exception.MissingValueException;
import ru.practicum.shareit.item.dto.InCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
            builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()
        );
    }

    public ResponseEntity<Object> addItem(ItemDto itemDto, Long userId) {
        if (itemDto.getName() == null ||
            itemDto.getDescription() == null ||
            itemDto.getAvailable() == null) {
            throw new MissingValueException();
        }
        return post("", userId, itemDto);
    }

    public ResponseEntity<Object> editItem(ItemDto itemDto, Long userId, Long itemId) {
        return patch(String.format("/%d", itemId), userId, itemDto);
    }

    public ResponseEntity<Object> getItem(Long itemId, Long userId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> getItems(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> findItems(String text) {
        return get("/search?text=" + text);
    }

    public ResponseEntity<Object> addComment(InCommentDto inCommentDto, Long itemId, Long userId) {
        return post(String.format("/%d/comment", itemId), userId, inCommentDto);
    }
}