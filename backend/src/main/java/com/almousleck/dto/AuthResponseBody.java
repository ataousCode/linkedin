package com.almousleck.dto;

import lombok.Data;

@Data
public class AuthResponseBody {
    private String token;
    private String message;

    public AuthResponseBody(String token, String message) {
        this.token = token;
        this.message = message;
    }
}
