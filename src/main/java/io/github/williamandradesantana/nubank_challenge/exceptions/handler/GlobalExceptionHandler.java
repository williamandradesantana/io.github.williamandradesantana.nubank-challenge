package io.github.williamandradesantana.nubank_challenge.exceptions.handler;

import io.github.williamandradesantana.nubank_challenge.exceptions.BusinessException;
import io.github.williamandradesantana.nubank_challenge.exceptions.ErrorResponse;
import io.github.williamandradesantana.nubank_challenge.exceptions.RequiredObjectIsNullException;
import io.github.williamandradesantana.nubank_challenge.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundExceptions(
            ResourceNotFoundException exception, WebRequest request
    ) {
        ErrorResponse response = ErrorResponse.builder()
            .timestamp(Instant.now())
            .message(exception.getMessage())
            .details(request.getDescription(false))
            .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleBusinessExceptions(
            BusinessException exception, WebRequest request
    ) {
        ErrorResponse response = ErrorResponse.builder()
            .timestamp(Instant.now())
            .message(exception.getMessage())
            .details(request.getDescription(false))
            .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RequiredObjectIsNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleRequiredObjectIsNullExceptions(
            RequiredObjectIsNullException exception, WebRequest request
    ) {
        ErrorResponse response = ErrorResponse.builder()
            .timestamp(Instant.now())
            .message(exception.getMessage())
            .details(request.getDescription(false))
            .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAllExceptions(
            Exception exception, WebRequest request
    ) {
        ErrorResponse response = ErrorResponse.builder()
            .timestamp(Instant.now())
            .message(exception.getMessage())
            .details(request.getDescription(false))
            .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
