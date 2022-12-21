package ru.practicum.shareit.server.item;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.server.user.User;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    @ManyToOne
    @JoinColumn(
            name = "item_id",
            referencedColumnName = "id"
    )
    private Item item;
    @OneToOne
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
    )
    private User author;

    public Comment() {
    }

    public Comment(long id, String text) {
        this.id = id;
        this.text = text;
    }

}
