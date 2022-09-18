package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.common.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
public class UserDto implements Dto {

    private long id;
    @NotBlank(message = "Name is mandatory")
    private final String name;
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is mandatory")
    private final String email;

}
