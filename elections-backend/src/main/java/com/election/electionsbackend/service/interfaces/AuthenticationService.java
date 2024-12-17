package com.election.electionsbackend.service.interfaces;


import com.election.electionsbackend.dto.JwtAuthenticationResponse;
import com.election.electionsbackend.dto.RefreshTokenRequest;
import com.election.electionsbackend.dto.SignInRequest;
import com.election.electionsbackend.dto.SignUpRequest;
import com.election.electionsbackend.model.user.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
