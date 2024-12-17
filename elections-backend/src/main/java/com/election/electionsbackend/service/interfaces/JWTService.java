package com.election.electionsbackend.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {

String extractUserName(String token);

String generateToken(UserDetails userdetails);

boolean isTokenValid(String token, UserDetails userdetails);

String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userdetails);
}
