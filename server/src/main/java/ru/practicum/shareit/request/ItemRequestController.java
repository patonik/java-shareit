package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.RequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<ItemRequestDto> addRequest(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody ItemRequestDto itemRequestDto) {
        ItemRequestDto created = requestService.addRequest(userId, itemRequestDto);
        log.info("itemRequestDto created: {}", created.toString());
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestDto>> getMyRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        List<ItemRequestDto> foundList = requestService.getMyRequests(userId);
        log.info("myItemRequestDtoList found: {}", foundList.toString());
        return new ResponseEntity<>(foundList, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> getAllRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        List<ItemRequestDto> foundList = requestService.getAllRequests(userId);
        log.info("itemRequestDtoList found: {}", foundList.toString());
        return new ResponseEntity<>(foundList, HttpStatus.OK);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ItemRequestDto> getRequest(@PathVariable("requestId") Long requestId) {
        ItemRequestDto found = requestService.getRequest(requestId);
        log.info("itemRequestDto found: {}", found.toString());
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

}
