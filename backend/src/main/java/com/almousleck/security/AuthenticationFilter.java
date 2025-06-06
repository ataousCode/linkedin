package com.almousleck.security;

import com.almousleck.model.User;
import com.almousleck.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthenticationFilter extends HttpFilter {
    private final List<String> unsecuredEndpoints = Arrays.asList(
            "/api/v1/authentication/login",
            "/api/v1/authentication/signup",
            "/api/v1/authentication/send-password-reset",
            "/api/v1/authentication/reset-password"
    );

    private final AuthService userService;
    private final JsonWebToken jsonWebToken;

    public AuthenticationFilter(AuthService userService,
                                JsonWebToken jsonWebToken) {
        this.userService = userService;
        this.jsonWebToken = jsonWebToken;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String path = request.getRequestURI();
        if (unsecuredEndpoints.contains(path)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String authorization = request.getHeader("Authorization");
            if (authorization == null || !authorization.startsWith("Bearer "))
                throw new ServletException("Token missing.");

            String token = authorization.substring(7);

            if (jsonWebToken.isTokenExpired(token))
                throw new ServletException("Invalid token");

            String email = jsonWebToken.getEmailFromToken(token);
            User user = userService.getUser(email);
            request.setAttribute("authenticatedUser", user);
            chain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Invalid authentication token, or token missing.\"}");
        }
    }
}
