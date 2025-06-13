package com.juanjooriveroo.jwtauthservice.security;

import com.juanjooriveroo.jwtauthservice.exception.UserNotFoundException;
import com.juanjooriveroo.jwtauthservice.model.User;
import com.juanjooriveroo.jwtauthservice.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.accessExpirationMs}")
    private long accessExpirationMs;

    @Value("${jwt.refreshExpirationMs}")
    private long refreshExpirationMs;

    private final UserRepository userRepository;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateAccessToken(User user) {
        logger.info("Generando access token para el usuario " + user.getId() + "...");
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("roles", user.getRoles().stream()
                        .map(role -> "ROLE_" + role.name())
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        logger.info("Token generado: " + token);
        logger.info("Expiracion: " + new Date(System.currentTimeMillis() + accessExpirationMs));
        return token;
    }

    public String generateRefreshToken(User user) {
        logger.info("Generando refresh token para el usuario " + user.getId() + "...");
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        logger.info("Token generado: " + token);
        logger.info("Expiracion: " + new Date(System.currentTimeMillis() + refreshExpirationMs));
        return token;
    }

    public boolean isTokenValid(String token) {
        logger.info("Validando token...");
        String jwt = token.replace("Bearer ", "");
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(jwt);
            logger.info("Token valido");
            return true;
        } catch (JwtException e) {
            logger.warn("Token invalido");
            return false;
        }
    }

    public Long extractUserId(String token) {
        logger.info("Extrayendo userId del token...");
        String jwt = token.replace("Bearer ", "");
        Claims claims = extractAllClaims(jwt);
        return Long.parseLong(claims.getSubject());
    }

    public List<String> extractRoles(String token) {
        logger.info("Extrayendo roles del token...");
        String jwt = token.replace("Bearer ", "");
        Claims claims = extractAllClaims(jwt);
        return claims.get("roles", List.class);
    }

    private Claims extractAllClaims(String token) {
        logger.info("Extrayendo claims del token...");
        String jwt = token.replace("Bearer ", "");
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    public User getUserFromToken(String token) {
        logger.info("Obteniendo usuario del token...");
        Long userId = extractUserId(token);
        logger.info("Usuario encontrado: " + userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
