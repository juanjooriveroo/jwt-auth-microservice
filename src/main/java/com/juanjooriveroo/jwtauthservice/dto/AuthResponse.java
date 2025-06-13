package com.juanjooriveroo.jwtauthservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @Schema(description = "Token de acceso", required = true)
    private String accessToken;

    @Schema(description = "Token de refresco", required = true)
    private String refreshToken;
}
