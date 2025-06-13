package com.juanjooriveroo.jwtauthservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotNull(message = "Identifier cannot be null")
    @NotBlank(message = "Identifier cannot be blank")
    @Size(min = 3, max = 255)
    @Schema(description = "Identificador de referencia del usuario", example = "usuario@ejemplo.com / alias123", required = true)
    private String identifier;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, max = 255)
    @Schema(description = "Contraseña del usuario", example = "Qwerty123456", required = true)
    private String password;
}