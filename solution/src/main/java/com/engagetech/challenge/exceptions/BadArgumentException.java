package com.engagetech.challenge.exceptions;

import org.springframework.http.HttpStatus;

public class BadArgumentException extends RestRuntimeException {

    public BadArgumentException(String message) {
        super(HttpStatus.BAD_REQUEST.toString(), message, null);
    }

}
