package com.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, String>>> handleGenericException(Exception ex) {
        Map<String, String> err = new HashMap<>();
        err.put("message", "Something went wrong. " + ex.getMessage());
        err.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString()); //500

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> err = new HashMap<>();
        err.put("message", "Argument not valid. " + ex.getMessage());
        err.put("status", HttpStatus.BAD_REQUEST.toString()); //400

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleValidationExceptions(IllegalArgumentException ex) {
        Map<String, String> err = new HashMap<>();
        err.put("message", "Argument not valid. " + ex.getMessage());
        err.put("status", HttpStatus.BAD_REQUEST.toString()); //400

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleNoSuchElementException(NoSuchElementException ex) {
        Map<String, String> err = new HashMap<>();
        err.put("message", "No such element. " + ex.getMessage());
        err.put("status", HttpStatus.NOT_FOUND.toString()); //404

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(err));
    }
}