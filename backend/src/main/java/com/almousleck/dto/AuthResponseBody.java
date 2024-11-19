package com.almousleck.dto;

import lombok.Data;

@Data
public class AuthResponseBody {
    private String token;
    private String message;
}
