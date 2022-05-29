package com.fullcycle.catalogovideo.controller.configuration;

import com.fullcycle.catalogovideo.domain.exceptions.DomainException;
import com.fullcycle.catalogovideo.domain.exceptions.NotFoundException;
import com.fullcycle.catalogovideo.domain.validation.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<?> handleDomainException(final DomainException ex) {
        return ResponseEntity.unprocessableEntity()
                .body(ApiError.from(ex));
    }

    record ApiError(String message, List<Error> errors) {
        static ApiError from(final DomainException ex){
            return new ApiError(ex.getMessage(), ex.getErrors());
        }
    }

}
