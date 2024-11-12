package com.example.job_portal.exception;

import com.example.job_portal.entity.api_response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
//        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
//        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}