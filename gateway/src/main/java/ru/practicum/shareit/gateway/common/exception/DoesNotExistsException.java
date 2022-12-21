package ru.practicum.shareit.gateway.common.exception;

public class DoesNotExistsException extends RuntimeException {
    public DoesNotExistsException(String message) {
        super(message);
    }
}
