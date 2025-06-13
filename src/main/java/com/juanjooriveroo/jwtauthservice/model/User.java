package com.juanjooriveroo.jwtauthservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un usuario del sistema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    @Column(unique = true)
    @Schema(description = "Identificador primario único (email, nombre de usuario, etc.)", example = "usuario@ejemplo.com")
    private String identifier1;

    @Column(unique = true)
    @Schema(description = "Identificador secundario único (teléfono, DNI, etc.)", example = "12345678A")
    private String identifier2;

    @Column(nullable = false)
    @Schema(description = "Contraseña cifrada del usuario", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Roles asignados al usuario", allowableValues = {"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"}, example = "[\"USER\", \"ADMIN\"]")
    private List<Role> roles;
}