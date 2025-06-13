#  Microservicio de Autenticación con JWT

Este es un microservicio genérico de autenticación de usuarios desarrollado con **Spring Boot** y **JWT**. Ha sido diseñado para ser reutilizable, seguro y personalizable, cumpliendo con las mejores prácticas de desarrollo moderno.

---

## **Características principales**

-  **Registro, login y autenticación.**
- Soporta **dos identificadores únicos** (como correo o nombre de usuario).
- Generación y validación de tokens **JWT de acceso y refresco**.

-  **Roles y seguridad.**
- Gestión de roles (`ADMIN`, `USER`, `MODERATOR`).
- Endpoints protegidos con JWT y acceso al **Actuator** limitado a ADMIN.

-  **Documentación automática con Swagger/OpenAPI.**
- Explora y prueba todos los endpoints fácilmente en tu navegador.

-  **Deploy con Docker.**
- Dockerfile para el microservicio y `docker-compose.yml` para levantar el servicio junto a PostgreSQL.

-  **Configuración flexible.**
- Modifica tiempos de expiración de los tokens o la clave secreta desde `application.properties`.
- Crea roles en función de lo que necesites desde 'model/Role'.

---

## **Tecnologías usadas**

- **Backend:** Spring Boot (Spring MVC, Spring Security, Spring Data JPA).
- **Base de datos:** PostgreSQL con Hibernate (ORM).
- **Seguridad:** JWT (Json Web Token) y encriptación con BCrypt.
- **Infraestructura:** Docker y Docker Compose.
- **Documentación:** Swagger (SpringDoc OpenAPI).

---

## **Pre-requisitos**

1. **Herramientas necesarias:**
    - Docker y Docker Compose instalados en tu sistema.
    - JDK 21 instalado en tu máquina.

2. **Configura las siguientes dependencias:**
    - `application.properties` ya incluye configuraciones por defecto, pero puedes personalizarlas según lo que necesites.

3. **Tecnologías complementarias instaladas:**
    - Postman (opcional) para pruebas manuales.

---

## **Instrucciones para levantar el proyecto**

1. **Clonar este repositorio:**  
   Ejecuta el siguiente comando:

   ```bash
   git clone https://github.com/juanjooriveroo/jwt-auth-microservice.git
   cd jwt-auth-microservice
   ```

2. **Construir y levantar los contenedores:**  
   Dentro de la carpeta del proyecto, ejecuta:

   ```bash
   docker-compose up --build
   ```

3. **Probar el microservicio:**  
   Accede a las URLs de Swagger y documentación:
    - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    - Documentación API: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)
    
    O úsala directamente implementandola en tu proyecto.
---

## **Configuración del proyecto**

### Propiedades importantes (`application.properties`)
Configuraciones clave para personalizar el comportamiento del microservicio:

```properties
# Llave secreta para JWT (reemplázala en producción).
jwt.secret=supersecretkeyjwtforprojectjuanjo1234supersecretkey

# Tiempos de expiración de tokens
jwt.accessExpirationMs=3600000     # 1 hora en milisegundos
jwt.refreshExpirationMs=2592000000 # 30 días en milisegundos
```

---

## **Endpoints principales**

| Métodos | Endpoint            | Descripción                       | Seguridad             |
|---------|---------------------|-----------------------------------|-----------------------|
| POST    | `/auth/register`    | Registra un nuevo usuario         | Público               |
| POST    | `/auth/login`       | Inicia sesión                     | Público               |
| POST    | `/auth/validate`    | Valida el token si es funcional   | Público con token     |
| POST    | `/auth/refresh`     | Renueva el token de acceso        | Público con token     |
| GET     | `/actuator/**`      | Estadísticas del sistema          | Solo rol ADMIN        |
| PUT     | `/user/password`    | Cambiar la contraseña del usuario | Autenticado           |
| PUT     | `/user/identifiers` | Actualizar identificadores        | Autenticado           |

---

## **Contribuciones**

Este es un proyecto personal, pero si tienes ideas para mejorar la seguridad, agregar características o solucionar errores, ¡siéntete libre de abrir un issue o un pull request!

---

## **Autor**
**Juan José Rivero Lorido**  
juanjoriverolorido@gmail.com  
[GitHub](https://github.com/juanjooriveroo)
[Linkedin](https://www.linkedin.com/in/juanjooriveroo/)

---

## **Licencia**

Este proyecto se distribuye bajo la licencia [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0).  
