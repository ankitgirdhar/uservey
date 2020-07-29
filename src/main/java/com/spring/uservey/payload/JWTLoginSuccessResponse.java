package com.spring.uservey.payload;

import lombok.Getter;
import lombok.Setter;

public class JWTLoginSuccessResponse {

    @Getter
    @Setter
    private boolean success;

    @Getter
    @Setter
    private String token;

    public JWTLoginSuccessResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    @Override
    public String toString() {
        return "JWTLoginSuccessResponse{" +
                "success=" + success +
                ", token='" + token + '\'' +
                '}';
    }
}
