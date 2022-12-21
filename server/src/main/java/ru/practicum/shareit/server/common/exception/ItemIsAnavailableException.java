package ru.practicum.shareit.server.common.exception;

public class ItemIsAnavailableException extends RuntimeException {
    public ItemIsAnavailableException(String message) {
        super(message);
    }
}
