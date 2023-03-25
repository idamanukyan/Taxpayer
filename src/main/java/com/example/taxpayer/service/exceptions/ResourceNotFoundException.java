package com.example.taxpayer.service.exceptions;


import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApplicationException {


    public ResourceNotFoundException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public ResourceNotFoundException(Integer id) {
        super(HttpStatus.NOT_FOUND, String.format("Resource with id : {%s} not found", id.toString()));
    }

    public String getMessage() {
        return super.getMessage();
    }

    public HttpStatus status() {
        return super.status();
    }

}
