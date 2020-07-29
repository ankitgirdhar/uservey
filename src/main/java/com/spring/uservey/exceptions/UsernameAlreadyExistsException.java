package com.spring.uservey.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String s) {
        super(s);
    }
}
