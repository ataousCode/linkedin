package com.almousleck.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequestBody {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Enter a valid email address")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;

    public AuthRequestBody(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
