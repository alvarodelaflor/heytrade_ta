package com.heytrade.pokedex.validation.impl;

import com.heytrade.pokedex.validation.ValidateDecimalConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateDecimalConstraintImpl implements ConstraintValidator<ValidateDecimalConstraint, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Double value = (Double) o;

        boolean check01 = value.toString().split("\\.")[1].length() <= 2;
        boolean check02 = value >= 0. && value <= 1.0;

        return check01 && check02;
    }
}
