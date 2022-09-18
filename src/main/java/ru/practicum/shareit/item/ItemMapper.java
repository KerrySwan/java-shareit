package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.IMapper;

@Component
public class ItemMapper implements IMapper<Item, ItemDto> {
    @Override
    public ItemDto toDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner(),
                item.getRequest()
        );
    }

    @Override
    public Item toModel(ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                itemDto.getOwner(),
                itemDto.getRequest()
        );
    }
}
