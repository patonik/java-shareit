package ru.practicum.shareit.exception;

public class MissingValueException extends DataOperationException {
    public MissingValueException() {
        super();
    }

    public MissingValueException(String message) {
        super(message);
    }

    public MissingValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
