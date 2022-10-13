package ru.practicum.shareit.common.exception;

public class ItemIsAnavailableException extends RuntimeException {
    public ItemIsAnavailableException(String message) {
        super(message);
    }
}
