package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.Comment;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
public class ItemRequestControllerMockTest {

    @MockBean
    ItemRequestService itemRequestService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    private User u = new User(1L, "name", "user@email.com");
    private Item i = new Item(1L, "text", "desc", true, 1L, 1L);
    private ItemRequestDto req = new ItemRequestDto(
            1L,
            "desc",
            UserMapper.toDto(u),
            LocalDateTime.of(2022, 10, 10, 10, 10, 10),
            List.of(
                    ItemMapper.toDto(i)
            )
    );

    @Test
    void createRequest() throws Exception{
        Mockito.when(itemRequestService.add(anyLong(), any()))
                .thenReturn(req);
        mvc.perform(post("/requests")
                        .content(mapper.writeValueAsString(req))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(req)));
    }

    @Test
    void getRequests() throws Exception{
        Mockito.when(itemRequestService.getByUserId(anyLong()))
                .thenReturn(List.of(req));
        mvc.perform(get("/requests")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(req))));
    }

    @Test
    void getRequestsAsPage() throws Exception{
        Mockito.when(itemRequestService.getAllAsPage(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of(req));
        mvc.perform(get("/requests/all")
                        .param("from", "0")
                        .param("size", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(req))));
    }

    @Test
    void getRequestById() throws Exception{
        Mockito.when(itemRequestService.getByRequestId(anyLong(), anyLong()))
                .thenReturn(req);
        mvc.perform(get("/requests/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(req)));
    }

}
