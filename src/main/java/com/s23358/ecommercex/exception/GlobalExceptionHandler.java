package com.s23358.ecommercex.exception;

import com.s23358.ecommercex.res.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<?>> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
          Response.<String>builder()
                  .statusCode(HttpStatus.NOT_FOUND.value())
                  .message(ex.getMessage())
                  .data(null)
                  .build()
        );

    }

}
