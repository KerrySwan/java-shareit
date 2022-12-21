package ru.practicum.shareit.server.item;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return itemService.getAll(userId, from, size);
    }

    @GetMapping(path = "/{itemId}")
    public ItemDto get(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                       @PathVariable(value = "itemId") long itemId) {
        return itemService.getItem(userId, itemId);
    }

    @PostMapping
    public ItemDto create(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                          @RequestBody ItemDto itemDto) {
        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping(path = "/{itemId}")
    public ItemDto update(@PathVariable(value = "itemId") long itemId,
                          @RequestHeader(value = "X-Sharer-User-Id") long userId,
                          @RequestBody ItemDto itemDto) {
        itemDto.setId(itemId);
        return itemService.updateItem(userId, itemDto);
    }

    @GetMapping(path = "/search")
    public List<ItemDto> search(@RequestParam(value = "text") String pattern,
                                @RequestParam(value = "from") int from,
                                @RequestParam(value = "size") int size) {
        return itemService.find(pattern, from, size);
    }

    @PostMapping(path = "/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                 @PathVariable(value = "itemId") long itemId,
                                 @RequestBody CommentDto commentDto) {
        return itemService.addComment(userId, itemId, commentDto);
    }

}



