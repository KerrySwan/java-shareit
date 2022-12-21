package ru.practicum.shareit.gateway.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/items")
public class ItemController {

    private final ItemClient itemClient;

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId) {
        return itemClient.getAll(userId);
    }

    @GetMapping(path = "/{itemId}")
    public ResponseEntity<Object> get(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                      @PathVariable(value = "itemId") @Positive long itemId) {
        return itemClient.getItem(userId, itemId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                         @RequestBody @Valid ItemDto itemDto) {
        return itemClient.addItem(userId, itemDto);
    }

    @PatchMapping(path = "/{itemId}")
    public ResponseEntity<Object> update(@PathVariable(value = "itemId") @Positive long itemId,
                                         @RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                         @RequestBody ItemDto itemDto) {
        itemDto.setId(itemId);
        return itemClient.updateItem(userId, itemDto);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<Object> search(@RequestParam(value = "text") String pattern,
                                         @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                         @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return itemClient.find(pattern, from, size);
    }

    @PostMapping(path = "/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                             @PathVariable(value = "itemId") long itemId,
                                             @RequestBody @Valid CommentDto commentDto) {
        return itemClient.addComment(userId, itemId, commentDto);
    }

}



