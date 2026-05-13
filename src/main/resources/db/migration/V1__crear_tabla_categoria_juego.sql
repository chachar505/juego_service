CREATE TABLE categoria (

    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion  VARCHAR(255)

);

CREATE TABLE juego (
        id_juego BIGINT AUTO_INCREMENT PRIMARY KEY,
        nombre_juego VARCHAR(150) NOT NULL UNIQUE,
        descripcion  VARCHAR(500),
        precio  DECIMAL(10, 2) NOT NULL,
        stock INT  NOT NULL DEFAULT 0,
        activo  BOOLEAN  NOT NULL DEFAULT TRUE,
        categoria_id BIGINT   NOT NULL,
        CONSTRAINT fk_juego_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (id_categoria)

);