package com.agenda.utils.annotations;


import com.agenda.utils.TimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * The Interface ValidTime
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 14/08/2024
 */
@Constraint(validatedBy =  TimeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTime  {

    String message() default "Invalid time. It must be in the format HH:mm.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
