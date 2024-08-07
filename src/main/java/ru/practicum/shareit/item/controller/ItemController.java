package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import ru.practicum.shareit.item.dto.OutCommentDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Slf4j
@Validated
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestBody @NotNull @Valid ItemDto itemDto,
                                           @RequestHeader("X-Sharer-User-Id") @NotNull Long userId) {
        ItemDto created = itemService.addItem(itemDto, userId);
        log.info("ItemDto created: {}", created.toString());
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> editItem(@RequestBody @NotNull @Valid ItemDto itemDto,
                                            @RequestHeader("X-Sharer-User-Id") @NotNull @Min(1) Long userId,
                                            @PathVariable @NotNull @Min(1) Long itemId) {
        ItemDto updated = itemService.editItem(itemDto, userId, itemId);
        log.info("ItemDto updated: {}", updated.toString());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable @NotNull @Min(1) Long itemId,
                                           @RequestHeader("X-Sharer-User-Id") @NotNull @Min(1) Long userId) {
        ItemDto found = itemService.getItem(itemId, userId);
        log.info("ItemDto found: {}", found.toString());
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems(@RequestHeader("X-Sharer-User-Id") @NotNull @Min(1) Long userId) {
        List<ItemDto> found = itemService.getItems(userId);
        log.info("ItemDtoList found: {}", found.toString());
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> findItems(@RequestParam(value = "text", required = false) String text) {
        List<ItemDto> found = itemService.findItems(text);
        log.info("ItemDtoList searched by text and found: {}", found.toString());
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<OutCommentDto> addComment(@RequestBody @NotNull @Valid InCommentDto inCommentDto,
                                                    @PathVariable @NotNull @Min(1) Long itemId,
                                                    @RequestHeader("X-Sharer-User-Id") @NotNull @Min(1) Long userId) {
        OutCommentDto created = itemService.addComment(inCommentDto, itemId, userId);
        log.info("OutCommentDto created: {}", created.toString());
        return new ResponseEntity<>(created, HttpStatus.OK);
    }
}
