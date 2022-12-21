package ru.practicum.shareit.server.request;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto add(long requesterId, ItemRequestDto dto);

    List<ItemRequestDto> getByUserId(long userId);

    List<ItemRequestDto> getAllAsPage(long userId, int from, int size);

    ItemRequestDto getByRequestId(long userId, long requestId);


}
