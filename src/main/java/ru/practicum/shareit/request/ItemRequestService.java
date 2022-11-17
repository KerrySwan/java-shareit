package ru.practicum.shareit.request;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto add(long requesterId, ItemRequestDto dto);

    List<ItemRequestDto> getByUserId(long userId);

    Page<ItemRequestDto> getAllAsPage(int from, int size);

    ItemRequestDto getByRequestId(long requestId);


}
