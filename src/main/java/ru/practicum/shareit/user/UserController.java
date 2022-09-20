package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{userId}")
    public UserDto get(@PathVariable(name = "userId") @Positive long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getUsers();
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PatchMapping(path = "/{userId}")
    public UserDto patch(@PathVariable(name = "userId") @Positive long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userService.updateUser(userDto);
    }

    @DeleteMapping(path = "/{userId}")
    public void delete(@PathVariable(name = "userId") @Positive long id) {
        userService.deleteUser(id);
    }

}
