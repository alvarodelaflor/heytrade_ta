package com.heytrade.pokedex.validation.impl;

import com.heytrade.pokedex.validation.ValidateDecimalConstraint;
import com.heytrade.pokedex.validation.WeightValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class WeightValidatorImpl implements ConstraintValidator<WeightValidator, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        int splitIndex = s.indexOf(".") + 3;
        String quantity = s.trim().substring(0, splitIndex);
        String unit = s.trim().substring(splitIndex, s.trim().length());

        String decimalPattern = "([0-9]*)\\.([0-9]*)";
        boolean check01 = Pattern.matches(decimalPattern, quantity);

        String weightPattern = "(lbs?|oz|g|kg)";
        boolean check02 = Pattern.matches(weightPattern, unit);

        return check01 && check02;
    }
}
