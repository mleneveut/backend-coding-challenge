package com.engagetech.challenge.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RestRuntimeException {

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.toString(), message, null);
    }

    public ResourceNotFoundException(String code, String message) {
        super(code, message, null);
    }

    public ResourceNotFoundException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

}
