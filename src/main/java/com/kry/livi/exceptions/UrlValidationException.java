package com.kry.livi.exceptions;

import org.springframework.http.HttpStatus;

public class UrlValidationException extends BaseRestException {
    public UrlValidationException(String message) {
        super(message);
    }

    public UrlValidationException(Throwable cause, String message) {
        super(cause, message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getDisplayMessage() {
        return "The URL should have a valid schema";
    }
}
