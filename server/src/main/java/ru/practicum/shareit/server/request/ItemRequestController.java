package ru.practicum.shareit.server.request;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto add(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                              @RequestBody ItemRequestDto dto) {
        return itemRequestService.add(userId, dto);
    }

    @GetMapping
    public List<ItemRequestDto> getByUserId(@RequestHeader(value = "X-Sharer-User-Id") long userId) {
        return itemRequestService.getByUserId(userId);
    }

    @GetMapping(path = "/all")
    public List<ItemRequestDto> getAllAsPage(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                             @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return itemRequestService.getAllAsPage(userId, from, size);
    }

    @GetMapping(path = "/{requestId}")
    public ItemRequestDto getByRequestId(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                         @PathVariable(value = "requestId") long requestId) {
        return itemRequestService.getByRequestId(userId, requestId);
    }


}
