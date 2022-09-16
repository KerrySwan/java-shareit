package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.common.Model;

/**
 * TODO Sprint add-controllers.
 */
@AllArgsConstructor
@Getter
@Setter
public class User implements Model {

    private long id;
    private String name;
    private String email;

    public User update(User user){
        if(user.getName() != null) this.setName(user.getName());
        if(user.getEmail() != null) this.setEmail(user.getEmail());
        return this;
    }

}
