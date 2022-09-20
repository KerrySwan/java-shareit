package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId) {
        return itemService.getAll(userId);
    }

    @GetMapping(path = "/{itemId}")
    public ItemDto get(@PathVariable(value = "itemId") @Positive long itemId) {
        return itemService.getItem(itemId);
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
    public List<ItemDto> search(@RequestParam(value = "text") String pattern) {
        return itemService.find(pattern);
    }

}



