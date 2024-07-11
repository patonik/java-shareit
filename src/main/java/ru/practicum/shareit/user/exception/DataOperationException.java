package ru.practicum.shareit.user.exception;

public class DataOperationException extends RuntimeException {
    public DataOperationException() {
        super();
    }

    public DataOperationException(String message) {
        super(message);
    }

    public DataOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
