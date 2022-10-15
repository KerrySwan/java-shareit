package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    @NotBlank(message = "Name is mandatory")
    private final String name;
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is mandatory")
    private final String email;
    private long id;

}
