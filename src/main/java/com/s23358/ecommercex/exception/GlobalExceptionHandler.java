package com.s23358.ecommercex.exception;

import com.s23358.ecommercex.res.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<Void>> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
          Response.<Void>builder()
                  .statusCode(HttpStatus.NOT_FOUND.value())
                  .message(ex.getMessage())
                  .build()
        );

    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response<Void>> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Response.<Void>builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build()
        );

    }

}
