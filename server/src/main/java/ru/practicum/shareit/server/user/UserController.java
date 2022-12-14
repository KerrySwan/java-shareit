package ru.practicum.shareit.server.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{userId}")
    public UserDto get(@PathVariable(name = "userId") long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getUsers();
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PatchMapping(path = "/{userId}")
    public UserDto patch(@PathVariable(name = "userId") long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userService.updateUser(userDto);
    }

    @DeleteMapping(path = "/{userId}")
    public void delete(@PathVariable(name = "userId") long id) {
        userService.deleteUser(id);
    }

}
