package ru.practicum.shareit.gateway.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private long id;
    @NotBlank(message = "Name is mandatory")
    private final String name;
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is mandatory")
    private final String email;

}
