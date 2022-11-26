package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero int from,
                                @RequestParam(value = "size", required = false, defaultValue = "10") @Positive int size) {
        return itemService.getAll(userId, from, size);
    }

    @GetMapping(path = "/{itemId}")
    public ItemDto get(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                       @PathVariable(value = "itemId") @Positive long itemId) {
        return itemService.getItem(userId, itemId);
    }

    @PostMapping
    public ItemDto create(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                          @RequestBody @Valid ItemDto itemDto) {
        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping(path = "/{itemId}")
    public ItemDto update(@PathVariable(value = "itemId") @Positive long itemId,
                          @RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                          @RequestBody ItemDto itemDto) {
        itemDto.setId(itemId);
        return itemService.updateItem(userId, itemDto);
    }

    @GetMapping(path = "/search")
    public List<ItemDto> search(@RequestParam(value = "text") String pattern,
                                @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero int from,
                                @RequestParam(value = "size", required = false, defaultValue = "10") @Positive int size) {
        return itemService.find(pattern, from, size);
    }

    @PostMapping(path = "/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                 @PathVariable(value = "itemId") long itemId,
                                 @RequestBody @Valid CommentDto commentDto) {
        return itemService.addComment(userId, itemId, commentDto);
    }

}



