package com.projects.sounds_api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> treat404(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> treat400(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> treatBadRequest(BadRequestException badRequestException) {
        return ResponseEntity.badRequest().build();
    }

}
