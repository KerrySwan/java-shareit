package ru.practicum.shareit.server.request;

import ru.practicum.shareit.server.item.ItemMapper;
import ru.practicum.shareit.server.user.UserMapper;

import java.util.Collections;
import java.util.stream.Collectors;

public class ItemRequestMapper {

    public static ItemRequestDto toDto(ItemRequest model) {
        return new ItemRequestDto(
                model.getId(),
                model.getDescription(),
                UserMapper.toDto(model.getRequester()),
                model.getCreated(),
                model.getItems() == null ? Collections.emptyList() : model.getItems().stream().map(ItemMapper::toDto).collect(Collectors.toList())
        );
    }

    public static ItemRequest toModel(ItemRequestDto dto) {
        return new ItemRequest(
                dto.getId(),
                dto.getDescription(),
                UserMapper.toModel(dto.getRequester()),
                dto.getCreated(),
                dto.getItems() == null ? Collections.emptyList() : dto.getItems().stream().map(ItemMapper::toModel).collect(Collectors.toList())
        );
    }

}
