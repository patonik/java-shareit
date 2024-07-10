package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestBody ItemDto itemDto,
                                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        ItemDto created = itemService.addItem(itemDto, userId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> editItem(@RequestBody ItemDto itemDto,
                                            @RequestHeader("X-Sharer-User-Id") Long userId) {
        ItemDto updated = itemService.editItem(itemDto, userId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable Long itemId) {
        ItemDto found = itemService.getItem(itemId);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        List<ItemDto> found = itemService.getItems(userId);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> findItems(@RequestParam("text") String text) {
        List<ItemDto> found = itemService.findItems(text);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }
}
