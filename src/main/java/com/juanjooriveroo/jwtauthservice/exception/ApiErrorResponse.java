package com.juanjooriveroo.jwtauthservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Respuesta estándar para errores en la API")
@Data
@AllArgsConstructor
public class ApiErrorResponse {
    @Schema(description = "Código de estado HTTP", example = "401")
    private int status;

    @Schema(description = "Descripción breve del tipo de error", example = "Unauthorized")
    private String error;

    @Schema(description = "Mensaje detallado del error", example = "Credenciales inválidas")
    private String message;
}