package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
@Validated
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto add(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                              @RequestBody ItemRequestDto dto) {
        return itemRequestService.add(userId, dto);
    }

    @GetMapping
    public List<ItemRequestDto> getByUserId(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId) {
        return itemRequestService.getByUserId(userId);
    }

    @GetMapping(path = "/all")
    public List<ItemRequestDto> getAllAsPage(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                             @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero int from,
                                             @RequestParam(value = "size", required = false, defaultValue = "10") @Positive int size) {
        return itemRequestService.getAllAsPage(userId, from, size);
    }

    @GetMapping(path = "/{requestId}")
    public ItemRequestDto getByRequestId(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                         @PathVariable(value = "requestId") @Positive long requestId) {
        return itemRequestService.getByRequestId(userId, requestId);
    }


}
