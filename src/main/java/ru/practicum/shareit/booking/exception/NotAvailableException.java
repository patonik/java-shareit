package ru.practicum.shareit.booking.exception;

public class NotAvailableException extends RuntimeException {
    public NotAvailableException() {
        super();
    }

    public NotAvailableException(String message) {
        super(message);
    }

    public NotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
