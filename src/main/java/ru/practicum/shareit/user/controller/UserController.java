package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid @NotNull UserDto userDto) {
        UserDto created = userService.addUser(userDto);
        log.info("userDto created: " + created.toString());
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> editUser(@RequestBody @Valid @NotNull UserDto userDto,
                                            @PathVariable @Min(1) Long userId) {
        UserDto updated = userService.editUser(userDto, userId);
        log.info("userDto updated: " + updated.toString());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable @Min(1) Long userId) {
        UserDto found = userService.getUser(userId);
        log.info("userDto found: " + found.toString());
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> found = userService.getUsers();
        log.info("userDto found: " + found.toString());
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable @Min(1) Long userId) {
        UserDto deleted = userService.deleteUser(userId);
        log.info("userDto deleted: " + deleted.toString());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
