package ru.practicum.shareit.booking.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.shareit.booking.dto.InwardBookingDto;

import java.time.LocalDateTime;

public class BookingValidator implements ConstraintValidator<ValidBooking, InwardBookingDto> {
    @Override
    public void initialize(ValidBooking constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(InwardBookingDto inwardBookingDto, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime start = inwardBookingDto.getStart();
        LocalDateTime end = inwardBookingDto.getEnd();
        if (start != null && end != null) {
            return start.isBefore(end);
        }
        return false;
    }
}
