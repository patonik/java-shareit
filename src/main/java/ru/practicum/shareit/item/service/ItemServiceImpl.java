package ru.practicum.shareit.item.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.item.dto.InCommentDto;
import ru.practicum.shareit.item.dto.InCommentMapper;
import ru.practicum.shareit.item.dto.OutCommentDto;
import ru.practicum.shareit.item.dto.OutCommentMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.exception.MissingValueException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ItemMapper itemMapper;
    private final InCommentMapper inCommentMapper;
    private final OutCommentMapper outCommentMapper;
    private final CommentRepository commentRepository;

    public ItemDto addItem(ItemDto itemDto, Long userId) {
        if (itemDto.getName() == null ||
            itemDto.getDescription() == null ||
            itemDto.getAvailable() == null) {
            throw new MissingValueException();
        }
        userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        itemDto.setId(null);
        Item created =
            itemRepository.save(itemMapper.toEntity(itemDto, userId));
        return itemMapper.toDto(created, created.getUser(), null, null, null);
    }

    public ItemDto editItem(ItemDto itemDto, Long userId, Long itemId) {
        Item item = itemRepository.findByUserIdAndId(userId, itemId).orElseThrow(EntityNotFoundException::new);
        itemDto.setId(itemId);
        Item updated =
            itemRepository.save(itemMapper.updateEntity(item, itemDto));
        return itemMapper.toDto(updated, updated.getUser(), null, null, null);
    }

    public ItemDto getItem(Long itemId, Long userId) {
        Item found = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        LocalDateTime now = LocalDateTime.now();

        Booking last = null;
        Booking next = null;
        if (userId.equals(found.getUser().getId())) {
            last = bookingRepository.findFirstByItemIdAndEndBeforeOrderByEndDesc(itemId, now);
            next = bookingRepository.findFirstByItemIdAndStartAfterOrderByStartAsc(itemId, now);
        }
        Set<String> comments = commentRepository.findCommentTextByItemId(itemId);
        return itemMapper.toDto(found, found.getUser(), last, next, comments);
    }

    public List<ItemDto> getItems(Long userId) {
        List<Item> found = itemRepository.findAllByUserId(userId);
        return found.stream().map(x -> itemMapper.toDto(x, x.getUser(), null, null, null)).toList();
    }

    public List<ItemDto> findItems(String text) {
        if (StringUtils.isBlank(text)) {
            return Collections.emptyList();
        }
        List<Item> found = itemRepository.findByNameOrDescriptionAndAvailable(text);
        return found.stream().map(x -> itemMapper.toDto(x, x.getUser(), null, null, null)).toList();
    }

    @Override
    public OutCommentDto addComment(InCommentDto inCommentDto, Long itemId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        if (!bookingRepository.existsByItemIdAndBookerIdAndEndIsLessThanEqual(itemId, userId, LocalDateTime.now())) {
            throw new AccessException();
        }
        Comment comment = commentRepository.save(inCommentMapper.toEntity(inCommentDto, item, user));
        return outCommentMapper.toDto(comment);
    }
}
