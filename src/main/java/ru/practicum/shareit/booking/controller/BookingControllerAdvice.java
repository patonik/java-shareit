package ru.practicum.shareit.booking.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.shareit.booking.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotFoundException;


@RestControllerAdvice
@Slf4j
public class BookingControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotAvailableException.class)
    public ErrorResponse handleNotAvailableException(final NotAvailableException e) {
        log.warn("NotAvailableException: {}", e.getMessage());
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        log.warn("MethodArgumentTypeMismatchException: {}", e.getMessage());
        ProblemDetail problemDetail =
            ProblemDetail.forStatus(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
        problemDetail.setProperty("error", "Unknown " + e.getPropertyName() + ": " + e.getValue());
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), problemDetail, e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.warn("NotFoundException: {}", e.getMessage());
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), e);
    }
}
