package ru.practicum.shareit.request;

import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    @PostMapping
    public ItemRequestDto add(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                              @RequestBody ItemRequestDto dto){
        return null;
    }

    @GetMapping
    public ItemRequestDto getByUserId(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId){
        return null;
    }

    @GetMapping(path = "/all")
    public ItemRequestDto getAllAsPage(@RequestHeader(value = "X-Sharer-User-Id") @Positive long userId,
                                 @RequestParam(value = "from") long from,
                                 @RequestParam(value = "size") long size){
        return null;
    }

    @GetMapping(path = "/{requestId}")
    public ItemRequestDto getByRequestId(@PathVariable(value = "requestId") @Positive long requestId){
        return null;
    }


}
