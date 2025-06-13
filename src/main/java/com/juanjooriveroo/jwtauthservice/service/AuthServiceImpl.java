package com.juanjooriveroo.jwtauthservice.service;

import com.juanjooriveroo.jwtauthservice.dto.*;
import com.juanjooriveroo.jwtauthservice.exception.InvalidCredentialsException;
import com.juanjooriveroo.jwtauthservice.exception.InvalidTokenException;
import com.juanjooriveroo.jwtauthservice.exception.UserAlreadyExistsException;
import com.juanjooriveroo.jwtauthservice.exception.UserNotFoundException;
import com.juanjooriveroo.jwtauthservice.model.User;
import com.juanjooriveroo.jwtauthservice.repository.UserRepository;
import com.juanjooriveroo.jwtauthservice.security.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Transactional
    @Override
    public AuthResponse register(RegisterRequest request) {
        logger.info("Registrando usuario...");
        if (userRepository.existsByIdentifier(request.getIdentifier1())) {
            logger.warn("Identifier1 ya en uso");
            throw new UserAlreadyExistsException("Identifier already in use");
        }

        if (request.getIdentifier2() != null && userRepository.existsByIdentifier(request.getIdentifier2())) {
            logger.warn("Identifier2 ya en uso");
            throw new UserAlreadyExistsException("Identifier already in use");
        }

        User user = User.builder()
                .identifier1(request.getIdentifier1())
                .identifier2(request.getIdentifier2())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .build();

        logger.info("Guardando el usuario...");
        userRepository.save(user);

        logger.info("Creando tokens...");
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken);
    }


    @Transactional
    @Override
    public AuthResponse login(AuthRequest request) {
        logger.info("Logueando usuario...");
        User user = userRepository.findByIdentifier(request.getIdentifier())
                .orElse(null);

        if (user == null) {
            logger.warn("Usuario no encontrado");
            throw new UserNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Password incorrecto");
            throw new InvalidCredentialsException("Invalid credentials");
        }

        logger.info("Creando tokens...");
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken);
    }


    @Transactional
    @Override
    public TokenValidationResponse validateToken(String token) {
        logger.info("Validando token...");
        if (!jwtUtil.isTokenValid(token)) {
            logger.warn("Token invalido");
            throw new InvalidTokenException("Invalid token");
        }

        Long userId = jwtUtil.extractUserId(token);
        List<String> roles = jwtUtil.extractRoles(token);

        logger.info("Extraidos datos: userId=" + userId + ", roles=" + roles);
        return new TokenValidationResponse(true, userId, roles);
    }

    @Transactional
    @Override
    public RefreshTokenResponse refreshToken(String token) {
        logger.info("Actualizando token...");
        if (!jwtUtil.isTokenValid(token)) {
            logger.warn("Token invalido");
            throw new InvalidTokenException("Invalid refresh token");
        }

        Long userId = jwtUtil.extractUserId(token);
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            logger.warn("Usuario no encontrado");
            throw new UserNotFoundException("User not found");
        }

        String newAccessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        logger.info("Tokens creados con éxito: {}     {}", newAccessToken, newRefreshToken);
        return new RefreshTokenResponse(newAccessToken, newRefreshToken);
    }
}