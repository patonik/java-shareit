package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.RequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;
    private UserRepository userRepository;
    private RequestMapper requestMapper;

    @Override
    public ItemRequestDto addRequest(Long userId, ItemRequestDto itemRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(AccessException::new);
        ItemRequest itemRequest = requestMapper.toEntity(itemRequestDto, user);
        return requestMapper.toDto(requestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDto> getAllRequests(Long userId) {
        userRepository.findById(userId).orElseThrow(AccessException::new);
        return requestRepository.findAllByRequesterIdNot(userId);
    }

    @Override
    public List<ItemRequestDto> getMyRequests(Long userId) {
        userRepository.findById(userId).orElseThrow(AccessException::new);
        return requestRepository.findAllByRequesterId(userId);
    }

    @Override
    public ItemRequestDto getRequest(Long requestId) {
        ItemRequest itemRequest = requestRepository.findById(requestId).orElseThrow(NotFoundException::new);
        return requestMapper.toDto(itemRequest);
    }
}
