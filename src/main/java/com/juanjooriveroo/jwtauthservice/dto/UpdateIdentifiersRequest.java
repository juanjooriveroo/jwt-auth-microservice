package com.juanjooriveroo.jwtauthservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIdentifiersRequest {

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255)
    @Schema(description = "Contraseña del usuario", example = "Qwerty123456", required = true)
    private String password;

    @Size(min = 8, max = 255)
    @Schema(description = "Identificador 1 del usuario (Se necesita minimo un identificador)", example = "Alias123", required = false)
    private String identifier1;

    @Size(min = 8, max = 255)
    @Schema(description = "Identificador 2 del usuario (Se necesita minimo un identificador)", example = "ejemplo@correo.com", required = false)
    private String identifier2;
}
