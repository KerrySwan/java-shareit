package ru.practicum.shareit.gateway.common.exception;

public class ItemIsAnavailableException extends RuntimeException {
    public ItemIsAnavailableException(String message) {
        super(message);
    }
}
