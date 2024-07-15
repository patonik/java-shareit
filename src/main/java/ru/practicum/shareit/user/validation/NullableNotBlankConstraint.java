package ru.practicum.shareit.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringFieldValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullableNotBlankConstraint {
    String message() default "Invalid text data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
