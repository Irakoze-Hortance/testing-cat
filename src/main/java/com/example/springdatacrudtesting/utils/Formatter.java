package com.example.springdatacrudtesting.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Formatter {

    public static ResponseEntity<?> send(Object data, HttpStatus status) {
        return ResponseEntity.status(status).body(data);
    }

    public static ResponseEntity<?> ok(Object data) {
        try {
            return ResponseEntity.ok(data);
        } catch (CustomException exception) {
            return ResponseEntity.status(exception.getHttpStatus()).body(exception.getMessage());
        }
    }
}
