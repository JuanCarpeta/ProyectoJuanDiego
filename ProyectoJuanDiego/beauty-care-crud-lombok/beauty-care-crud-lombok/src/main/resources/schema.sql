-- Tabla de productos. IF NOT EXISTS la hace segura de ejecutar en cada arranque.
CREATE TABLE IF NOT EXISTS producto (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre      TEXT    NOT NULL,
    categoria   TEXT    NOT NULL,
    stock       INTEGER NOT NULL DEFAULT 0 CHECK (stock >= 0),
    descripcion TEXT
);
