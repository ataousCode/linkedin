package com.almousleck.service;

import com.almousleck.dto.AuthRequestBody;
import com.almousleck.dto.AuthResponseBody;
import com.almousleck.model.User;
import com.almousleck.repository.UserRepository;
import com.almousleck.security.Encoder;
import com.almousleck.security.JsonWebToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final JsonWebToken jsonWebToken;
    private final Encoder encoder;

    public AuthResponseBody register(AuthRequestBody request) {
        User user = userRepository.save(new User(request.getEmail(),
                encoder.encode(request.getPassword())));
        String emailVerificationToken = generateEmailVerificationToken();
        String hashedToken = encoder.encode(emailVerificationToken);
        user.setEmailVerificationToken(hashedToken);
        int durationInMinutes = 1;
        user.setEmailVerificationTokenExpiryDate(LocalDateTime.now().plusMinutes(durationInMinutes));

        userRepository.save(user);

        String subject = "Email Verification";
        String body = String.format("""
                        Only one step to take full advantage of LinkedIn.
                        
                        Enter this code to verify your email: %s. The code will expire in %s minutes.""",
                emailVerificationToken, durationInMinutes);
        try {
            emailService.sendEmail(request.getEmail(), subject, body);
        }catch (Exception ex) {
            log.error("Error while sending email: {}", ex.getMessage());
        }
        String authenticationToken = jsonWebToken.generateToken(request.getEmail());
        return new AuthResponseBody(authenticationToken, "User registered success!");
    }

    public AuthResponseBody login(AuthRequestBody request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getEmail()));
        if (!encoder.matches(request.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid credentials");
        String token = jsonWebToken.generateToken(request.getEmail());
        return new AuthResponseBody(token, "Authenticated");
    }
    public static String generateEmailVerificationToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found"));
    }
}
