package com.almousleck.dto;

public class AuthResponseBody {
    private final String token;
    private final String message;

    public AuthResponseBody(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
