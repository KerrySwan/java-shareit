package ru.practicum.shareit.server.item;

public class CommentMapper {

    public static Comment toModel(CommentDto c) {
        return new Comment(
                c.getId(),
                c.getText()
        );
    }

    public static CommentDto toDto(Comment c) {
        return new CommentDto(
                c.getId(),
                c.getText(),
                c.getItem().getId(),
                c.getAuthor().getName()
        );
    }

}
