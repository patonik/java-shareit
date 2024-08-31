package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.InCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @Autowired
    public ItemController(ItemClient itemClient) {
        this.itemClient = itemClient;
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestBody @NotNull @Valid ItemDto itemDto,
                                          @RequestHeader("X-Sharer-User-Id") @NotNull Long userId) {
        log.info("requesting server to add item: {}, {}", userId, itemDto);
        return itemClient.addItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> editItem(@RequestBody @NotNull @Valid ItemDto itemDto,
                                            @RequestHeader("X-Sharer-User-Id") @NotNull @Min(1) Long userId,
                                            @PathVariable @NotNull @Min(1) Long itemId) {
        log.info("requesting server to edit item: {}, {}, {}", userId, itemId, itemDto);
        return itemClient.editItem(itemDto, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable @NotNull @Min(1) Long itemId,
                                           @RequestHeader("X-Sharer-User-Id") @NotNull @Min(1) Long userId) {
        log.info("requesting server to get item: {}, {}", userId, itemId);
        return itemClient.getItem(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getItems(@RequestHeader("X-Sharer-User-Id") @NotNull @Min(1) Long userId) {
        log.info("requesting server to get items");
        return itemClient.getItems(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findItems(@RequestParam(value = "text", required = false) String text) {
        log.info("requesting server to find items");
        return itemClient.findItems(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestBody @NotNull @Valid InCommentDto inCommentDto,
                                                    @PathVariable @NotNull @Min(1) Long itemId,
                                                    @RequestHeader("X-Sharer-User-Id") @NotNull @Min(1) Long userId) {
        log.info("requesting server to add comment: {}, {}, {}", userId, itemId, inCommentDto);
        return itemClient.addComment(inCommentDto, itemId, userId);
    }
}
