package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.RequestService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ItemRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestService requestService;

    private ItemRequestDto requestDto;

    @BeforeEach
    void setup() {
        // Initializing a mock request DTO
        requestDto = new ItemRequestDto();
        requestDto.setId(1L);
        requestDto.setDescription("Test item request");
    }

    @Test
    void addRequestTest() throws Exception {
        // Mock the service layer
        when(requestService.addRequest(1L, requestDto)).thenReturn(requestDto);

        // Perform the request
        mockMvc.perform(post("/requests")
                .header("X-Sharer-User-Id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\", \"description\":\"Test item request\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.description").value("Test item request"));
    }

    @Test
    void getMyRequestsTest() throws Exception {
        // Mock the service layer
        List<ItemRequestDto> requestList = Collections.singletonList(requestDto);
        when(requestService.getMyRequests(1L)).thenReturn(requestList);

        // Perform the request
        mockMvc.perform(get("/requests")
                .header("X-Sharer-User-Id", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].description").value("Test item request"));
    }

    @Test
    void getAllRequestsTest() throws Exception {
        // Mock the service layer
        List<ItemRequestDto> requestList = Arrays.asList(requestDto);
        when(requestService.getAllRequests(1L)).thenReturn(requestList);

        // Perform the request
        mockMvc.perform(get("/requests/all")
                .header("X-Sharer-User-Id", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].description").value("Test item request"));
    }

    @Test
    void getRequestTest() throws Exception {
        // Mock the service layer
        when(requestService.getRequest(1L)).thenReturn(requestDto);

        // Perform the request
        mockMvc.perform(get("/requests/{requestId}", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.description").value("Test item request"));
    }
}