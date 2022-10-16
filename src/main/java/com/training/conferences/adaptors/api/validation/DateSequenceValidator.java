package com.training.conferences.adaptors.api.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateSequenceValidator implements ConstraintValidator<SequenceFieldsMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final SequenceFieldsMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        final Object firstObj = beanWrapper.getPropertyValue(firstFieldName);
        final Object secondObj = beanWrapper.getPropertyValue(secondFieldName);

        boolean isBothLocalDateTime = firstObj instanceof LocalDate && secondObj instanceof LocalDate;

        if (!isBothLocalDateTime) return false;

        LocalDate firstDate = (LocalDate) firstObj;
        LocalDate secondDate = (LocalDate) secondObj;


        return  firstDate.isEqual(secondDate) || !firstDate.isAfter(secondDate) && !firstDate.isBefore(LocalDate.now());

    }
}