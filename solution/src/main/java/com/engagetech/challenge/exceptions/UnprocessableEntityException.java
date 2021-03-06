package com.engagetech.challenge.exceptions;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends RestRuntimeException {

    public UnprocessableEntityException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY.toString(), message, null);
    }

    public UnprocessableEntityException(Throwable cause) {
        super(HttpStatus.UNPROCESSABLE_ENTITY.toString(), cause.getMessage(), null);
    }

    public UnprocessableEntityException(String code, String message) {
        super(code, message, null);
    }


    public UnprocessableEntityException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
