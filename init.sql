-- Crear tabla users
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    identifier1 VARCHAR(255) UNIQUE,
    identifier2 VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Crear tabla user_roles (colección de roles)
CREATE TABLE user_roles (
    user_id INTEGER NOT NULL,
    role VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
