package ru.practicum.shareit.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        UserDto userDto = UserDto.builder().name("Mao").email("hunweibean1@javabeans.com").build();
        ItemDto itemDto = ItemDto.builder().name("nuclear warhead").description("伟大舵手领导的伟大革命").available(true).build();
        this.mockMvc.perform(post("/users").contentType("application/json").content(objectMapper.writeValueAsString(userDto))).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/items").contentType("application/json").header("X-Sharer-User-Id", 1).content(objectMapper.writeValueAsString(itemDto))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateExistingItem() throws Exception {
        this.mockMvc.perform(patch("/items/1").contentType("application/json").content("{" +
                "    \"available\": false" +
                "}").header("X-Sharer-User-Id", 1)).andDo(print()).andExpect(status().isOk());
    }

}