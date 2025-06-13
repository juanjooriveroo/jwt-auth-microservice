package com.juanjooriveroo.jwtauthservice.dto;

import com.juanjooriveroo.jwtauthservice.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull(message = "Identifier1 is required")
    @NotBlank(message = "Identifier1 is required")
    @Size(min = 3, max = 255)
    @Schema(description = "Identificador 1 del usuario", example = "Alias123", required = true)
    private String identifier1;

    @Size(min = 3, max = 255)
    @Schema(description = "Identificador 1 del usuario", example = "Alias123", required = false)
    private String identifier2;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255)
    @Schema(description = "Contraseña del usuario", example = "Qwerty123456", required = true)
    private String password;

    @NotNull(message = "Roles are required")
    @NotEmpty(message = "Roles are required")
    @Schema(description = "Lista de roles del usuario", allowableValues = {"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"}, example = "[\"USUARIO\", \"MODERADOR\"]", required = true)
    private List<Role> roles;
}