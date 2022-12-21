package ru.practicum.shareit.gateway.common.exception;

public class UnsupportedStatusException extends RuntimeException {
    public UnsupportedStatusException(String message) {
        super(message);
    }
}
