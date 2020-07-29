package com.spring.uservey.exceptions;

import lombok.Getter;
import lombok.Setter;

public class InvalidLoginResponse {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    public InvalidLoginResponse() {
        setUsername("Invalid username");
        setPassword("Invalid password");
    }
}
