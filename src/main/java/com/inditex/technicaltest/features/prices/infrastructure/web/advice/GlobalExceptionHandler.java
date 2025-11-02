package com.inditex.technicaltest.features.prices.infrastructure.web.advice;
import com.inditex.technicaltest.features.prices.domain.exception.PriceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(PriceNotFoundException ex) {
        return ResponseEntity.status(404).body(
                Map.of("timestamp", Instant.now().toString(), "status", 404, "error", ex.getMessage())
        );
    }
}
