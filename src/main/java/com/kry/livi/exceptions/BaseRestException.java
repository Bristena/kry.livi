package com.kry.livi.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BaseRestException extends RuntimeException {

    public BaseRestException(String message) {
        super(message);
    }

    public BaseRestException(Throwable cause, String message) {
        super(message, cause);
    }

    public abstract HttpStatus getHttpStatus();

    public abstract String getDisplayMessage();
}