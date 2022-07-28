package com.heytrade.pokedex.validation;

import com.heytrade.pokedex.validation.impl.ValidateDecimalConstraintImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target( { FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidateDecimalConstraintImpl.class)
public @interface ValidateDecimalConstraint {
    public String message() default "Invalid value: must be a decimal between 0.00 and 1.00 and use only two decimals";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
