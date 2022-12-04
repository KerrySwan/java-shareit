package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.booking.BookingStatus.APPROVED;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerMockTest {

    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private User u = new User(1L, "name", "user@email.com");
    private Item i = new Item(1L, "text", "desc", true, 1L, 1L);
    private BookingDto bookingDto = new BookingDto(
            1L,
            LocalDateTime.of(2023, 1, 1, 1, 1, 1),
            LocalDateTime.of(2023, 3, 1, 1, 1, 1),
            i,
            u,
            APPROVED
    );

    @Test
    void addBooking() throws Exception {
        Mockito.when(bookingService.add(anyLong(), any()))
                .thenReturn(bookingDto);
        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));

    }

    @Test
    void changeState() throws Exception {
        Mockito.when(bookingService.changeStatus(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(bookingDto);
        mvc.perform(patch("/bookings/1")
                        .param("approved", "true")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));

    }

    @Test
    void getBookingByIdAndUSerId() throws Exception {
        Mockito.when(bookingService.getByUserIdAndBookingId(anyLong(), anyLong()))
                .thenReturn(bookingDto);
        mvc.perform(get("/bookings/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));

    }

    @Test
    void getAllById() throws Exception {
        Mockito.when(bookingService.findAllByUserId(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingDto));
        mvc.perform(get("/bookings")
                        .param("from", "0")
                        .param("size", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(bookingDto))));

    }

    @Test
    void getAllByOwnerId() throws Exception {
        Mockito.when(bookingService.findAllByOwnerId(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingDto));
        mvc.perform(get("/bookings/owner")
                        .param("from", "0")
                        .param("size", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(bookingDto))));

    }

    @Test
    void getBookingByState() throws Exception {
        Mockito.when(bookingService.getByUserIdAndByState(anyLong(), anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingDto));
        mvc.perform(get("/bookings")
                        .param("state", "STATE")
                        .param("from", "0")
                        .param("size", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(bookingDto))));

    }

    @Test
    void getBookingByOwnerState() throws Exception {
        Mockito.when(bookingService.getOwnerIdAndByState(anyLong(), anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingDto));
        mvc.perform(get("/bookings/owner")
                        .param("state", "STATE")
                        .param("from", "0")
                        .param("size", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(bookingDto))));

    }


}
