package com.bonzenshop.BackendService.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static String tokenHeader = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestHeader = request.getHeader(tokenHeader);
        for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();)
            System.out.println(e.nextElement());
        System.out.println(request);
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            String  authToken = requestHeader.substring(7);
            JwtAuthentication authentication = new JwtAuthentication(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}