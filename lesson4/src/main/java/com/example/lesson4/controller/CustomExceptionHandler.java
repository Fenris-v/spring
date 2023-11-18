package com.example.lesson4.controller;

import com.example.lesson4.dto.response.ExceptionDto;
import com.example.lesson4.dto.response.ValidatorResponse;
import com.example.lesson4.exception.EntityNotFoundException;
import com.example.lesson4.exception.ForbiddenActionException;
import com.example.lesson4.exception.UnauthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException() {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionDto dto = new ExceptionDto("Not found", status.value());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(ForbiddenActionException.class)
    public ResponseEntity<Object> handleForbiddenActionException() {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ExceptionDto dto = new ExceptionDto("Forbidden", status.value());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException() {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ExceptionDto dto = new ExceptionDto("Unauthorized", status.value());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleCacheException(@NonNull DataIntegrityViolationException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionDto dto = new ExceptionDto(ex.getMessage(), status.value());
        return new ResponseEntity<>(dto, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> addError(errors, error));

        return new ResponseEntity<>(new ValidatorResponse(errors), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private static void addError(@NonNull Map<String, String> errors, @NonNull ObjectError error) {
        String fieldName = ((FieldError) error).getField();
        String message = error.getDefaultMessage();
        errors.put(fieldName, message);
    }
}
