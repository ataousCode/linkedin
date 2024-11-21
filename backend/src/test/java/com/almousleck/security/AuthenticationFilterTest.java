package com.almousleck.security;

import com.almousleck.model.User;
import com.almousleck.service.AuthService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @Mock
    private AuthService userService;
    @Mock
    private JsonWebToken jsonWebToken;
    @InjectMocks
    private AuthenticationFilter authenticationFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain chain;

    @BeforeEach
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        chain = new MockFilterChain();
    }

    @Test
    public void testUnsecuredEndpoints() throws ServletException, IOException {
        request.setRequestURI("/api/v1/authentication/login");

        authenticationFilter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    public void testSecuredEndpointWithValidToken() throws IOException, ServletException {
        String token = "validToken";
        User user = new User("testUser", "testEmail");

        given(jsonWebToken.isTokenExpired(token)).willReturn(false);
        given(jsonWebToken.getEmailFromToken(token)).willReturn("testEmail");
        given(userService.getUser(anyString())).willReturn(user);

        request.setRequestURI("/api/v1/secured/endpoint");
        request.addHeader("Authorization", "Bearer " + token);

        authenticationFilter.doFilter(request, response, chain);

        assertEquals("testUser", request.getAttribute("authenticatedUser"));
        verify(chain).doFilter(request, response);
    }

    @Test
    public void testSecuredEndpointWithExpiredToken() throws IOException, ServletException {
        String token = "expiredToken";
        given(jsonWebToken.isTokenExpired(token)).willReturn(true);
        request.setRequestURI("/api/v1/secured/endpoint");
        request.addHeader("Authorization", "Bearer " + token);
        authenticationFilter.doFilter(request, response, chain);
        assertEquals(401, response.getStatus());
        assertEquals("{\"message\": \"Invalid authentication token, or token missing.\"}", response.getContentAsString());
    }

    @Test
    public void testSecuredEndpointWithMissingToken() throws IOException, ServletException {
        request.setRequestURI("/api/v1/secured/endpoint");
        authenticationFilter.doFilter(request, response, chain);
        assertEquals(401, response.getStatus());
        assertEquals("{\"message\": \"Invalid authentication token, or token missing.\"}", response.getContentAsString());
    }
}