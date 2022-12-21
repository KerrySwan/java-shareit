package ru.practicum.shareit.server.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDto add(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                          @RequestBody BookingDtoIdOnly b) {
        return bookingService.add(userId, b);
    }

    @PatchMapping(path = "/{bookingId}")
    public BookingDto changeStatus(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                   @PathVariable long bookingId,
                                   @RequestParam(value = "approved") boolean isApproved) {
        return bookingService.changeStatus(userId, bookingId, isApproved);
    }

    @GetMapping(path = "/{bookingId}")
    public BookingDto getByUserIdAndBookingId(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                              @PathVariable long bookingId) {
        return bookingService.getByUserIdAndBookingId(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> getAllById(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                       @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return bookingService.findAllByUserId(userId, from, size);
    }

    @GetMapping(path = "/owner")
    public List<BookingDto> getAllByOwner(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                          @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return bookingService.findAllByOwnerId(userId, from, size);
    }

    @GetMapping(params = "state")
    public List<BookingDto> getByState(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                       @RequestParam String state,
                                       @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return bookingService.getByUserIdAndByState(userId, state, from, size);
    }

    @GetMapping(path = "/owner", params = "state")
    public List<BookingDto> getByOwnerState(@RequestHeader(value = "X-Sharer-User-Id") long ownerId,
                                            @RequestParam String state,
                                            @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return bookingService.getOwnerIdAndByState(ownerId, state, from, size);
    }

}

