package com.almousleck.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestBody {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Enter a valid email address")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
}
