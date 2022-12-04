package ru.practicum.shareit.server.request;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.server.item.Item;
import ru.practicum.shareit.server.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Table(name = "request")
@Entity
public class ItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String description;
    @ManyToOne
    @JoinColumn(
            name = "requester_id",
            referencedColumnName = "id"
    )
    private User requester;
    private LocalDateTime created;
    @Column(insertable = false, updatable = false)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "request_id",
            referencedColumnName = "id"
    )
    private List<Item> items = new ArrayList<Item>();

    public ItemRequest() {
    }

    public ItemRequest(long id, String description, User requester, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.requester = requester;
        this.created = created;
    }

    public ItemRequest(long id, String description, User requester, LocalDateTime created, List<Item> items) {
        this.id = id;
        this.description = description;
        this.requester = requester;
        this.created = created;
        this.items = items;
    }
}
