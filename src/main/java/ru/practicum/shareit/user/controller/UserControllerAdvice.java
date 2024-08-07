package ru.practicum.shareit.user.controller;

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
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.MissingValueException;

@RestControllerAdvice
@Slf4j
public class UserControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ErrorResponse handleConflictException(final ConflictException e) {
        log.warn("Conflict: {}", e.getMessage());
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.CONFLICT.value()), e);
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
    @ExceptionHandler(MissingValueException.class)
    public ErrorResponse handleMissingValueException(final MissingValueException e) {
        log.warn("MissingValueException: {}", e.getMessage());
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), e);
    }
}
