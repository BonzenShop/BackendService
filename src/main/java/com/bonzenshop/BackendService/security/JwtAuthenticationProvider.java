package com.bonzenshop.BackendService.security;

import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import com.bonzenshop.BackendService.service.JwtTokenService;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    private final JwtTokenService jwtService;

    @SuppressWarnings("unused")
    public JwtAuthenticationProvider() {
        this(null);
    }

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationProvider(JwtTokenService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            String token = (String) authentication.getCredentials();
            String username = jwtService.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            return jwtService.validateToken(token, userDetails)
                    .map(aBoolean -> new JwtAuthenticatedProfile(username))
                    .orElseThrow(() -> new JwtAuthenticationException("JWT Token validation failed"));

        } catch (JwtException ex) {
            log.error(String.format("Invalid JWT Token: %s", ex.getMessage()));
            throw new JwtAuthenticationException("Failed to verify token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}