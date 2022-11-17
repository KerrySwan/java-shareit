package ru.practicum.shareit.request;

public class ItemRequestMapper {

    public static ItemRequestDto toDto(ItemRequest model){
        return new ItemRequestDto(
                model.getId(),
                model.getDescription(),
                model.getRequester(),
                model.getCreated()
        );
    }

    public static ItemRequest toModel(ItemRequestDto dto){
        return new ItemRequest(
                dto.getId(),
                dto.getDescription(),
                dto.getRequester(),
                dto.getCreated()
        );
    }

}
