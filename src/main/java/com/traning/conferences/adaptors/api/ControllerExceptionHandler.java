package com.traning.conferences.adaptors.api;

import com.traning.conferences.exception.ConferenceException;
import com.traning.conferences.exception.UniqueConferenceNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getAllErrors().stream().findFirst().get().getDefaultMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConferenceException.class})
    public ResponseEntity<Object> handleConferenceException(ConferenceException ex) {
        Map<String, Object> body = new HashMap<>();

        var statusCode = HttpStatus.BAD_REQUEST;

        if (ex instanceof UniqueConferenceNameException)
            statusCode = HttpStatus.CONFLICT;

        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, statusCode);
    }
}