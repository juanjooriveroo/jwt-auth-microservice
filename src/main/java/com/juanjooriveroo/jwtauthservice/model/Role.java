package com.juanjooriveroo.jwtauthservice.model;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Role {
    @Schema(description = "Usuario regular")
    USER,

    @Schema(description = "Administrador con todos los permisos")
    ADMIN,

    @Schema(description = "Moderador con permisos intermedios")
    MODERATOR
}