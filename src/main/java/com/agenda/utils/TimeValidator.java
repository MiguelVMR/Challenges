package com.agenda.utils;

import com.agenda.utils.annotations.ValidTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The Class TimeValidator
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 14/08/2024
 */
public class TimeValidator implements ConstraintValidator<ValidTime, String> {

    private static final String TIME_PATTERN = "^([01]\\d|2[0-3]):[0-5]\\d$";

    private static final Pattern pattern = Pattern.compile(TIME_PATTERN);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (Objects.isNull(value) || value.isBlank()) {
            return false;
        }

        return pattern.matcher(value).matches();
    }
}
