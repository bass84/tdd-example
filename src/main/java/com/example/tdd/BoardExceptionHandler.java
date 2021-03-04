package com.example.tdd;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class BoardExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Void> handle(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Void> handle(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
