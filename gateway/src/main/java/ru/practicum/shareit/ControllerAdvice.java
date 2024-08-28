package ru.practicum.shareit;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.DataOperationException;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.exception.MissingValueException;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotFoundException;


@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotAvailableException.class)
    public ErrorResponse handleNotAvailableException(final NotAvailableException e) {
        log.warn("NotAvailableException: {}", e.getMessage());
        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
        problemDetail.setProperty("error", "entity not available");
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), problemDetail, e);
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ErrorResponse handleConflictException(final ConflictException e) {
        log.warn("Conflict: {}", e.getMessage());
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.CONFLICT.value()), e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingValueException.class)
    public ErrorResponse handleMissingValueException(final MissingValueException e) {
        log.warn("MissingValueException: {}", e.getMessage());
        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
        problemDetail.setProperty("error", "required value is missing");
        return new ErrorResponseException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), problemDetail, e);
    }

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
