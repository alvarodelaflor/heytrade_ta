package com.heytrade.pokedex.validation;

import com.heytrade.pokedex.validation.impl.HeightValidatorImpl;

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
@Constraint(validatedBy = HeightValidatorImpl.class)
public @interface HeightValidator {
    public String message() default "Invalid weight format. Example of valid format: 81.12kg (quantity plus unit wiht two letters)";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
