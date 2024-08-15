package com.agenda.application.controls;

import org.springframework.http.HttpStatus;

/**
 * The Class ApplicationException
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 14/08/2024
 */
public class ApplicationException extends RuntimeException {

    private final String message;

    private final HttpStatus status;

    public ApplicationException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
