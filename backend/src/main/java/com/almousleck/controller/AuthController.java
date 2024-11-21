package com.almousleck.controller;

import com.almousleck.dto.AuthRequestBody;
import com.almousleck.dto.AuthResponseBody;
import com.almousleck.model.User;
import com.almousleck.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/validate-email-verification-token")
    public String verifyEmail(@RequestParam String token, @RequestAttribute("authenticatedUser") User user) {
        authService.validateEmailVerificationToken(token, user.getEmail());
        return "Email verification successful";
    }

    @GetMapping("/send-email-verification-token")
    public String sendEmailVerificationToken(@RequestAttribute("authenticatedUser") User user) {
        authService.sendEmailVerificationToken(user.getEmail());
        return "Email verification token sent successfully.";
    }













}
