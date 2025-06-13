package com.juanjooriveroo.jwtauthservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Excepción lanzada cuando el identificador del usuario ya está registrado")
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

