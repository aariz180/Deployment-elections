package com.election.electionsbackend.controller;

import com.election.electionsbackend.dto.JwtAuthenticationResponse;
import com.election.electionsbackend.dto.RefreshTokenRequest;
import com.election.electionsbackend.dto.SignInRequest;
import com.election.electionsbackend.dto.SignUpRequest;
import com.election.electionsbackend.model.user.User;
import com.election.electionsbackend.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest) {
        if (signInRequest.getEmail() == null || signInRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        JwtAuthenticationResponse response = authenticationService.signIn(signInRequest);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        JwtAuthenticationResponse response = authenticationService.refreshToken(refreshTokenRequest);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(response);
    }
}
