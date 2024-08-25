package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface RequestService {
    ItemRequestDto addRequest(Long userId, ItemRequestDto itemRequestDto);

    List<ItemRequestDto> getAllRequests(Long userId);

    List<ItemRequestDto> getMyRequests(Long userId);

    ItemRequestDto getRequest(Long requestId);
}
