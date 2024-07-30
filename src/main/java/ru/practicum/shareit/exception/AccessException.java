package ru.practicum.shareit.exception;

public class AccessException extends RuntimeException {
    public AccessException() {
        super();
    }

    public AccessException(String message) {
        super(message);
    }

    public AccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
