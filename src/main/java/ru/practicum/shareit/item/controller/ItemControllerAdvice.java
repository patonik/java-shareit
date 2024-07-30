package ru.practicum.shareit.item.controller;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.DataOperationException;
import ru.practicum.shareit.exception.EntityNotFoundException;

@RestControllerAdvice
@Slf4j
public class ItemControllerAdvice {
    @ExceptionHandler(AccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccessException(final AccessException e) {
        log.warn("AccessException: {}", e.getMessage());
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException e) {
        log.warn("ConstraintViolation: {}", e.getMessage());
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValid: {}", e.getMessage());
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataOperationException.class)
    public ErrorResponse handleDataOperationException(final DataOperationException e) {
        log.warn("DataOperationException: {}", e.getMessage());
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(final EntityNotFoundException e) {
        log.warn("EntityNotFoundException: {}", e.getMessage());
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), e);
    }
}
