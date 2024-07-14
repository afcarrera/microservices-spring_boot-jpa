package com.carrera.bank.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
public class ValidationException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = 1L;
    public ValidationException(String message) {
        super(message);
    }
}