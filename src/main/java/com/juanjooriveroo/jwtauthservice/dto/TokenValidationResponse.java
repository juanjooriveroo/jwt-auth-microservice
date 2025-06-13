package com.juanjooriveroo.jwtauthservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationResponse {
    @Schema(description = "Booleano que indica si es valido el token", required = true)
    private Boolean valid;

    @Schema(description = "Identificador del usuario", example = "56", required = true)
    private Long userId;

    @Schema(description = "Lista de roles del usuario", allowableValues = {"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"}, example = "[\"USUARIO\", \"MODERADOR\"]", required = true)
    private List<String> roles;
}
