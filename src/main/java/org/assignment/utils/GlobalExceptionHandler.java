package org.assignment.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", ex.getStatusCode().value());
        errorResponse.put("error", HttpStatus.valueOf(ex.getStatusCode().value()).getReasonPhrase());
        errorResponse.put("message", ex.getReason());
        errorResponse.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }
}
