package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


/**
 * TODO Sprint add-controllers.
 */
@RestController
@Validated
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/users/{userId}")
    public UserDto get(@PathVariable(name = "userId") @Positive long id) {
        return userService.getUser(id);
    }

    @GetMapping(value = "/users")
    public List<UserDto> getAll() {
        return userService.getUsers();
    }

    @PostMapping(path = "/users")
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PatchMapping(value = "/users/{userId}")
    public UserDto patch(@PathVariable(name = "userId") @Positive long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userService.updateUser(userDto);
    }

    @DeleteMapping(path = "/users/{userId}")
    public void delete(@PathVariable(name = "userId") @Positive long id) {
        userService.deleteUser(id);
    }

}
