package ru.practicum.shareit.common.excepton;

public class DoesNotExistsException extends RuntimeException {
    public DoesNotExistsException(String message) {
        super(message);
    }
}
