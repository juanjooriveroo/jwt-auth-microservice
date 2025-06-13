# Utilizar la última imagen oficial de Amazon Corretto con soporte para Java 21.
FROM amazoncorretto:21

# Crear un directorio dentro del contenedor para la aplicación
WORKDIR /app

# Copiar el archivo JAR generado al contenedor
COPY target/jwt-auth-service-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponer el puerto en el que corre tu Spring Boot (según tu config: 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]