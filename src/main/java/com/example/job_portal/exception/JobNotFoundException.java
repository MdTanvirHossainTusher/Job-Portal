package com.example.job_portal.exception;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(String s) {
        super(s);
    }
}
