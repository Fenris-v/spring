package org.example.lesson5.controller;

import org.example.lesson5.dto.ExceptionDto;
import org.example.lesson5.dto.ValidatorResponse;
import org.example.lesson5.exception.CacheException;
import org.example.lesson5.exception.EntityNotFoundException;
import org.example.lesson5.exception.JsonParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ExceptionDto> handleJsonParseException() {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionDto dto = new ExceptionDto("JSON parse failed", status.value());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(CacheException.class)
    public ResponseEntity<Object> handleCacheException() {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionDto dto = new ExceptionDto("Getting data from cache was failed", status.value());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException() {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionDto dto = new ExceptionDto("Not found", status.value());
        return new ResponseEntity<>(dto, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
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
