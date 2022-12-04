package ru.practicum.shareit.gateway.request;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
@Validated
public class ItemRequestController {

    private final ItemReqiestClient itemReqiestClient;

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                      @RequestBody ItemRequestDto dto) {
        return itemReqiestClient.add(userId, dto);
    }

    @GetMapping
    public ResponseEntity<Object> getByUserId(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId) {
        return itemReqiestClient.getByUserId(userId);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Object> getAllAsPage(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                               @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(value = "size", required = false, defaultValue = "10") @Positive int size) {
        return itemReqiestClient.getAllAsPage(userId, from, size);
    }

    @GetMapping(path = "/{requestId}")
    public ResponseEntity<Object> getByRequestId(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                                 @PathVariable(value = "requestId") @Positive long requestId) {
        return itemReqiestClient.getByRequestId(userId, requestId);
    }


}