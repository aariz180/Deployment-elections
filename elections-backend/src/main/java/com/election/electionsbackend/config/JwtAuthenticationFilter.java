package com.election.electionsbackend.config;

import com.election.electionsbackend.service.interfaces.JWTService;
import com.election.electionsbackend.service.interfaces.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Dependency injection for JWTService
    private final JWTService jwtService;
    private final UserService userService;

    // Utility method to check if a string is not empty or null
    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    // The main method to filter incoming requests
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Retrieve the Authorization header from the HTTP request
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Check if the Authorization header is missing or doesn't start with "Bearer "
        if (StringUtils.isEmpty(authorizationHeader) || !org.apache.commons.lang.StringUtils.startsWith(authorizationHeader, "Bearer ")) {
            // If invalid, continue with the next filter in the chain and return early
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token by removing the "Bearer " prefix
        jwt = authorizationHeader.substring(7);

        // Use JWTService to extract the username (or email) encoded in the JWT
        userEmail = jwtService.extractUserName(jwt);

        // If the email is valid and the user is not yet authenticated
        if (isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Retrieve UserDetails from the UserService
            UserDetails userDetails = userService.getUserDetailsService().loadUserByUsername(userEmail);

            // Validate the JWT token against the user details (e.g., check expiration, signature, etc.)
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Create a new SecurityContext for the user
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                // Build a UsernamePasswordAuthenticationToken containing the user's details and authorities
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Attach additional request details to the authentication token
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication token in the SecurityContext
                securityContext.setAuthentication(token);

                // Update the SecurityContextHolder with the new context
                SecurityContextHolder.setContext(securityContext);
            }
        }
        // Proceed with the remaining filters in the chain
        filterChain.doFilter(request, response);
    }
}
