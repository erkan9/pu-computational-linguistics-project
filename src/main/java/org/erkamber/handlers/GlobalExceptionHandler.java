package org.erkamber.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(IOException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
    }
}