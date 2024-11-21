package com.almousleck.controller;

import com.almousleck.dto.AuthRequestBody;
import com.almousleck.dto.AuthResponseBody;
import com.almousleck.model.User;
import com.almousleck.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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


    @PutMapping("/profile/{id}")
    public User updateUserProfile(
            @RequestAttribute("authenticatedUser") User user,
            @PathVariable Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String location) {

        if (!user.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to update this profile.");
        }

        return authService.updateUserProfile(id, firstName, lastName, company, position, location);
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestAttribute("authenticatedUser") User user) {
        authService.deleteUser(user.getId());
        return "User deleted successfully.";
    }

    @GetMapping("/user")
    public User getUser(@RequestAttribute("authenticatedUser") User user) {
        return user;
    }













}
