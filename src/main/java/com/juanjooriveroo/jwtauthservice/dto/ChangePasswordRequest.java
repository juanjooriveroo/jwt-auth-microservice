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
public class ChangePasswordRequest {

    @NotNull(message = "Current password is required")
    @NotBlank(message = "Current password is required")
    @Size(min = 8, max = 255)
    @Schema(description = "Contraseña actual del usuario", example = "Qwerty123456", required = true)
    private String currentPassword;

    @NotNull(message = "New password is required")
    @NotBlank(message = "New password is required")
    @Size(min = 8, max = 255)
    @Schema(description = "Contraseña nueva del usuario", example = "Zxcvbn098765", required = true)
    private String newPassword;
}
