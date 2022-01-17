package com.kry.livi.exceptions;

import com.kry.livi.domain.ExceptionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExeptionHandler {

    private static final String GENERIC_ERROR = "The server has encountered an unknown error";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionInfo> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        if (ex instanceof BaseRestException) {
            HttpStatus httpStatus = ((BaseRestException) ex).getHttpStatus();
            return ResponseEntity.status(httpStatus.value())
                    .body((exceptionInfo(((BaseRestException) ex).getDisplayMessage())));
        } else if (ex instanceof UrlValidationException) {
            HttpStatus httpStatus = ((UrlValidationException) ex).getHttpStatus();
            return ResponseEntity.status(httpStatus.value())
                    .body((exceptionInfo(((UrlValidationException) ex).getDisplayMessage())));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((exceptionInfo(GENERIC_ERROR)));
        }
    }

    private ExceptionInfo exceptionInfo(String message) {
        return new ExceptionInfo(message);
    }
}
