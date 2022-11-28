package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
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
        dto.setRequester(UserMapper.toDto(userRepository.findById(requesterId).orElseThrow()));
        ItemRequest itemRequestDto = ItemRequestMapper.toModel(dto);
        itemRequestDto.setCreated(LocalDateTime.now());
        ItemRequest itemRequest = itemRequestRepository.save(itemRequestDto);
        return ItemRequestMapper.toDto(itemRequest);
    }

    @Override
    public List<ItemRequestDto> getByUserId(long userId) {
        userRepository.findById(userId).orElseThrow();
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterId(userId, descSortByCreation);
        return itemRequests.stream()
                .map(ItemRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> getAllAsPage(long userId, int from, int size) {
        Page<ItemRequest> itemRequests = itemRequestRepository.findAll(PageRequest.of((from / size), size, descSortByCreation));
        return itemRequests.stream()
                .filter(req -> req.getRequester().getId() != userId)
                .map(ItemRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getByRequestId(long userId, long requestId) {
        userRepository.findById(userId).orElseThrow();
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow();
        return ItemRequestMapper.toDto(itemRequest);
    }

}
