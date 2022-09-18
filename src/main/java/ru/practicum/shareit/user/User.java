package ru.practicum.shareit.user;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.common.Model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class User implements Model {

    private long id;
    private String name;
    private String email;

    public User() {
    }

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User update(User user) {
        if (user.getName() != null) this.setName(user.getName());
        if (user.getEmail() != null) this.setEmail(user.getEmail());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
