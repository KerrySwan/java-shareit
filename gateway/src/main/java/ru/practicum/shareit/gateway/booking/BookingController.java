package ru.practicum.shareit.gateway.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/bookings")
public class BookingController {

    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                      @RequestBody BookingDtoIdOnly b) {
        return bookingClient.add(userId, b);
    }

    @PatchMapping(path = "/{bookingId}")
    public ResponseEntity<Object> changeStatus(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                               @PathVariable @Positive long bookingId,
                                               @RequestParam(value = "approved") boolean isApproved) {
        return bookingClient.changeStatus(userId, bookingId, isApproved);
    }

    @GetMapping(path = "/{bookingId}")
    public ResponseEntity<Object> getByUserIdAndBookingId(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                                          @PathVariable @Positive long bookingId) {
        return bookingClient.getByUserIdAndBookingId(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllById(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                             @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero int from,
                                             @RequestParam(value = "size", required = false, defaultValue = "10") @Positive int size) {
        return bookingClient.findAllByUserId(userId, from, size);
    }

    @GetMapping(path = "/owner")
    public ResponseEntity<Object> getAllByOwner(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                                @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero int from,
                                                @RequestParam(value = "size", required = false, defaultValue = "10") @Positive int size) {
        return bookingClient.findAllByOwnerId(userId, from, size);
    }

    @GetMapping(params = "state")
    public ResponseEntity<Object> getByState(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                             @RequestParam String state,
                                             @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero int from,
                                             @RequestParam(value = "size", required = false, defaultValue = "10") @Positive int size) {
        return bookingClient.getUserIdAndByState(userId, state, from, size);
    }

    @GetMapping(path = "/owner", params = "state")
    public ResponseEntity<Object> getByOwnerState(@RequestHeader(value = "X-Sharer-User-Id") @Positive long ownerId,
                                                  @RequestParam String state,
                                                  @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero int from,
                                                  @RequestParam(value = "size", required = false, defaultValue = "10") @Positive int size) {
        return bookingClient.getOwnerIdAndByState(ownerId, state, from, size);
    }

}
