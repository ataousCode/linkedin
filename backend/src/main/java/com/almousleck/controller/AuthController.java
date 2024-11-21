package com.almousleck.controller;

import com.almousleck.dto.AuthRequestBody;
import com.almousleck.dto.AuthResponseBody;
import com.almousleck.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponseBody register(@Valid @RequestBody AuthRequestBody request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseBody login(@Valid @RequestBody AuthRequestBody request) {
        return authService.login(request);
    }
}
