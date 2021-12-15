package com.murad.todoApp.exceptions;


import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class ApiException extends RuntimeException {

    private String errorMessage;
    private HttpStatus httpStatus;

    public ApiException() {
    }

    public ApiException(String errorMessage) {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorMessage = errorMessage;
    }

    public ApiException(String errorMessage, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public ApiException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.errorMessage = httpStatus.getReasonPhrase();
    }
}
