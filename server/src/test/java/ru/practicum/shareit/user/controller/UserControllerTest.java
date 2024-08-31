package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserControllerTest {
    private final MockMvc mockMvc;
    private static BufferedReader bufferedReader;
    private static final Random random = new Random();
    private Long id;

    @MockBean
    private final UserService userService;

    private UserDto userDto;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        try {
            bufferedReader = new BufferedReader(new FileReader("../words.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        bufferedReader.close();
    }

    @BeforeEach
    void setup() throws IOException {
        // Initializing a mock user DTO
        String firstName = bufferedReader.readLine();
        String surName = bufferedReader.readLine();
        String domain = bufferedReader.readLine();
        userDto = new UserDto();
        id = random.nextLong(0, 500);
        userDto.setId(id);
        userDto.setName("%s %s".formatted(firstName, surName));
        userDto.setEmail("%s.%s@%s.com".formatted(firstName, surName, domain));
    }

    @Test
    void addUserTest() throws Exception {
        // Mock the service layer
        when(userService.addUser(userDto)).thenReturn(userDto);

        // Perform the request
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"%d\", \"name\":\"%s\", \"email\":\"%s\"}".formatted(
                        userDto.getId(),
                        userDto.getName(),
                        userDto.getEmail()
                    )
                )
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(userDto.getName()))
            .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    @Test
    void editUserTest() throws Exception {
        // Mock the service layer
        UserDto updatedUser = new UserDto();
        long newId = random.nextLong(0, 500);
        updatedUser.setId(newId);
        String firstName = bufferedReader.readLine();
        String surName = bufferedReader.readLine();
        String domain = bufferedReader.readLine();
        updatedUser.setName("%s %s".formatted(firstName, surName));
        updatedUser.setEmail("%s.%s@%s.com".formatted(firstName, surName, domain));

        when(userService.editUser(updatedUser, id)).thenReturn(updatedUser);

        // Perform the request
        mockMvc.perform(patch("/users/{userId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"%d\", \"name\":\"%s\", \"email\":\"%s\"}".formatted(updatedUser.getId(),
                    updatedUser.getName(), updatedUser.getEmail())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(newId))
            .andExpect(jsonPath("$.name").value(updatedUser.getName()))
            .andExpect(jsonPath("$.email").value(updatedUser.getEmail()));
    }

    @Test
    void getUserTest() throws Exception {
        // Mock the service layer
        when(userService.getUser(id)).thenReturn(userDto);

        // Perform the request
        mockMvc.perform(get("/users/{userId}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value(userDto.getName()))
            .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    @Test
    void getUsersTest() throws Exception {
        // Mock the service layer
        List<UserDto> users = Arrays.asList(userDto);
        when(userService.getUsers()).thenReturn(users);

        // Perform the request
        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(id))
            .andExpect(jsonPath("$[0].name").value(userDto.getName()))
            .andExpect(jsonPath("$[0].email").value(userDto.getEmail()));
    }

    @Test
    void deleteUserTest() throws Exception {
        // Mock the service layer
        when(userService.deleteUser(id)).thenReturn(userDto);

        // Perform the request
        mockMvc.perform(delete("/users/{userId}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value(userDto.getName()))
            .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }
}
