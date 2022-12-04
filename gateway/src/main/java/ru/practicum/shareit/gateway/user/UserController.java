package ru.practicum.shareit.gateway.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserClient userClient;

    @GetMapping(path = "/{userId}")
    public ResponseEntity<Object> get(@PathVariable(name = "userId") @Positive long id) {
        return userClient.getUser(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return userClient.getUsers();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid UserDto userDto) {
        return userClient.addUser(userDto);
    }

    @PatchMapping(path = "/{userId}")
    public ResponseEntity<Object> patch(@PathVariable(name = "userId") @Positive long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userClient.updateUser(userDto);
    }

    @DeleteMapping(path = "/{userId}")
    public void delete(@PathVariable(name = "userId") @Positive long id) {
        userClient.deleteUser(id);
    }

}
