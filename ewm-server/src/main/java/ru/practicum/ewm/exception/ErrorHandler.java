package ru.practicum.ewm.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;


@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationException handleValidationException(final ValidationException e) {
        return e;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public EntityNotFoundException handleEntityNotFoundException(final EntityNotFoundException e) {
        return e;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ConstraintViolationException handleConstraintViolationException(final ConstraintViolationException e) {
        return e;
    }

}
