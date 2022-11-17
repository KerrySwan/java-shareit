package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Table(name = "request")
public class ItemRequest {

    private long id;
    private String description;
    @ManyToOne
    @JoinColumn(
            name = "requestor_id",
            referencedColumnName = "id"
    )
    private User requester;
    private LocalDateTime created;

}
