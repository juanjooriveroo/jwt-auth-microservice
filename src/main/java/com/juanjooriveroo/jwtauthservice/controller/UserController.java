package com.juanjooriveroo.jwtauthservice.controller;

import com.juanjooriveroo.jwtauthservice.dto.ChangePasswordRequest;
import com.juanjooriveroo.jwtauthservice.dto.DeleteUserRequest;
import com.juanjooriveroo.jwtauthservice.dto.UpdateIdentifiersRequest;
import com.juanjooriveroo.jwtauthservice.service.UserService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Endpoints para la gestión de datos de usuarios")
public class UserController {

    private final UserService userService;

    @PutMapping("/identifiers")
    @Operation(
            summary = "Actualizar identificadores",
            description = "Endpoint para actualizar los identificadores de un usuario autenticado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Identificadores actualizados correctamente",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Contraseña actual incorrecta",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token no válido",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Identificador/es ya en uso",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    public ResponseEntity<String> updateIdentifiers(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para actualizar los identificadores del usuario",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UpdateIdentifiersRequest.class),
                            mediaType = "application/json"
                    )
            )
            @RequestBody UpdateIdentifiersRequest request,
            @Parameter(
                    description = "Token JWT con formato Bearer {token}",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                    schema = @Schema(type = "String")
            )
            @RequestHeader("Authorization") String token) {
        if (userService.updateIdentifiers(request, token)) {
            return ResponseEntity.ok("Identifiers updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid current password");
        }
    }

    @PutMapping("/password")
    @Operation(
            summary = "Cambiar contraseña",
            description = "Endpoint para cambiar la contraseña de un usuario autenticado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contraseña actualizada correctamente",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Contraseña actual inválida",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token no válido",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    public ResponseEntity<String> changePassword(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para cambiar la contraseña del usuario",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ChangePasswordRequest.class),
                            mediaType = "application/json"
                    )
            )
            @RequestBody ChangePasswordRequest request,
            @Parameter(
                    description = "Token JWT con formato Bearer {token}",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                    schema = @Schema(type = "string")
            )
            @RequestHeader("Authorization") String token) {
        if (userService.changePassword(request, token)){
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid current password");
        }
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "Eliminar usuario",
            description = "Endpoint para eliminar la cuenta del usuario autenticado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario eliminado correctamente",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos para la eliminación",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token no válido",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    public ResponseEntity<String> deleteUser(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para confirmar la eliminación del usuario",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = DeleteUserRequest.class),
                            mediaType = "application/json"
                    )
            )
            @RequestBody DeleteUserRequest request,
            @Parameter(
                    description = "Token JWT con formato Bearer {token}",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                    schema = @Schema(type = "string")
            )
            @RequestHeader("Authorization") String token){
        if (userService.deleteUser(request, token)) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }
}