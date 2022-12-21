package ru.practicum.shareit.gateway.common.exception;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message) {
        super(message);
    }
}
