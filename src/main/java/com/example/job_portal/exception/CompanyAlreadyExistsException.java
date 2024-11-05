package com.example.job_portal.exception;

public class CompanyAlreadyExistsException extends RuntimeException {
    public CompanyAlreadyExistsException(String message) {
        super(message);
    }
}
