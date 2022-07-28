package com.heytrade.pokedex.validation.impl;

import com.heytrade.pokedex.validation.HeightValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class HeightValidatorImpl implements ConstraintValidator<HeightValidator, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        int splitIndex = s.indexOf(".") + 3;
        String quantity = s.trim().substring(0, splitIndex);
        String unit = s.trim().substring(splitIndex, s.trim().length());

        String decimalPattern = "([0-9]*)\\.([0-9]*)";
        boolean check01 = Pattern.matches(decimalPattern, quantity);

        String weightPattern = "(kms?|m|cm|mm)";
        boolean check02 = Pattern.matches(weightPattern, unit);

        return check01 && check02;
    }
}
