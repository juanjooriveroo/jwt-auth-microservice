package com.juanjooriveroo.jwtauthservice.service;

import com.juanjooriveroo.jwtauthservice.dto.*;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
    TokenValidationResponse validateToken(String token);
    RefreshTokenResponse refreshToken(String token);
}
