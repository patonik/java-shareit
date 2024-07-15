package ru.practicum.shareit.item.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringFieldValidator implements ConstraintValidator<NullableNotBlankConstraint, String> {
    @Override
    public void initialize(NullableNotBlankConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || !s.isBlank();
    }
}
