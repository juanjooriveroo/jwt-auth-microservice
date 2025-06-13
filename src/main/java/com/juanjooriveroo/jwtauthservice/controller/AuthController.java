package com.juanjooriveroo.jwtauthservice.controller;

import com.juanjooriveroo.jwtauthservice.dto.*;
import com.juanjooriveroo.jwtauthservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para la autenticación de usuarios")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(
            summary = "Registrar usuarios",
            description = "Endpoint para registrar un usuario y obtener sus tokens JWT",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registro exitoso. Devuelve los tokens.",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Identificador/es ya en uso.",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    public ResponseEntity<AuthResponse> register(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de registro del usuario",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RegisterRequest.class),
                            mediaType = "application/json"
                    )
            )
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Endpoint para autenticar un usuario y obtener sus tokens JWT",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticación exitosa. Devuelve los tokens.",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciales inválidas.",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuario no encontrado",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    public ResponseEntity<AuthResponse> login(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de autenticación del usuario",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AuthRequest.class),
                            mediaType = "application/json"
                    )
            )
            @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/validate")
    @Operation(
            summary = "Validar token",
            description = "Endpoint que valida el token adjuntado en el Header de la petición",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token válido y funcional. Devuelve \"true\", id del usuario y sus roles",
                            content = @Content(schema = @Schema(implementation = TokenValidationResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token no válido",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    public ResponseEntity<TokenValidationResponse> validateToken(
            @Parameter(
                    description = "Token JWT con formato Bearer {token}",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                    schema = @Schema(type = "String")
            )
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.validateToken(token));
    }

    @GetMapping("/refresh")
    @Operation(
            summary = "Refrescar token",
            description = "Endpoint que refresca la duración del token adjuntado en el Header de la petición",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token válido. Devuelve los tokens nuevos.",
                            content = @Content(schema = @Schema(implementation = RefreshTokenResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token no válido",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuario no encontrado",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @Parameter(
                    description = "Token JWT con formato Bearer {token}",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                    schema = @Schema(type = "String")
            )
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.refreshToken(token));
    }
}