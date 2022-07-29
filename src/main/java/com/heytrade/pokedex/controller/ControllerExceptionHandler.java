package com.heytrade.pokedex.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.heytrade.pokedex.domain.ErrorMessage;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {DuplicateKeyException.class})
    public ResponseEntity<ErrorMessage> handleDuplicateKeyExceptionVException(DuplicateKeyException ex, WebRequest request) {
        String dupKey = ex.getMostSpecificCause().getMessage().split("dup key: ")[1].split("'")[0];
        ErrorMessage message = new ErrorMessage(
                false,
                LocalDateTime.now(),
                dupKey,
                "Duplicate keys are no allowed");

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> handleValidationExceptions( MethodArgumentNotValidException ex) {

        Map<String, List<String>> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                error -> ((FieldError) error).getField(), Collectors.mapping(
                                        error -> error.getDefaultMessage(), Collectors.toList()
                                )
                        )
                );

        ErrorMessage message = new ErrorMessage(
                false,
                LocalDateTime.now(),
                errors.toString(),
                "Invalid params");

        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFormatExceptions( InvalidFormatException ex) {

        ErrorMessage message = new ErrorMessage(
                false,
                LocalDateTime.now(),
                ex.getLocalizedMessage().split("\\n")[0],
                "Invalid param format"
        );
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
