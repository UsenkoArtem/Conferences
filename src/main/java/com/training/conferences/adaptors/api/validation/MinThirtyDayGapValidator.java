package com.training.conferences.adaptors.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MinThirtyDayGapValidator implements ConstraintValidator<MinThirtyDayGapMatch, LocalDate> {
    @Override
    public void initialize(MinThirtyDayGapMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null)
            return false;

        long between = ChronoUnit.DAYS.between(LocalDate.now(), localDate);
        return between >= 30;
    }

}
