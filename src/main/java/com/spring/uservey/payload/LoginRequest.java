package com.spring.uservey.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @Getter
    @Setter
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Getter
    @Setter
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
