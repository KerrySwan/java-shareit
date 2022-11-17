package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final UserRepository userRepository;
    private final ItemRequestRepository itemRequestRepository;

    Sort descSortByCreation = Sort.by(Sort.Direction.DESC, "created");

    @Override
    public ItemRequestDto add(long requesterId, ItemRequestDto dto) {
        dto.setRequester(userRepository.getById(requesterId));
        ItemRequest itemRequestDto = ItemRequestMapper.toModel(dto);
        ItemRequest itemRequest = itemRequestRepository.save(itemRequestDto);
        return ItemRequestMapper.toDto(itemRequest);
    }

    @Override
    public List<ItemRequestDto> getByUserId(long userId) {
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterId(userId, descSortByCreation);
        return itemRequests.stream()
                .map(ItemRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ItemRequestDto> getAllAsPage(int from, int size) {
        Page<ItemRequest> itemRequests = itemRequestRepository.findAll(PageRequest.of(from, size, descSortByCreation));
        List<ItemRequestDto> p = itemRequests.stream()
                .map(ItemRequestMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(p);
    }

    @Override
    public ItemRequestDto getByRequestId(long requestId) {
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow();
        return ItemRequestMapper.toDto(itemRequest);
    }

}
