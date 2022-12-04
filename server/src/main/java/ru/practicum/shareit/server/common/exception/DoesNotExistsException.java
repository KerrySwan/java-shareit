package ru.practicum.shareit.server.common.exception;

public class DoesNotExistsException extends RuntimeException {
    public DoesNotExistsException(String message) {
        super(message);
    }
}
