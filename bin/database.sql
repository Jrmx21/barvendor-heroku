DROP DATABASE IF EXISTS RestauranteDB;

CREATE DATABASE IF NOT EXISTS RestauranteDB;

USE RestauranteDB;

-- Creación de tablas
CREATE TABLE Productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Producto VARCHAR(255) NOT NULL,
    Categoria ENUM(
        'Bebida',
        'Entrante',
        'Principal'
    ) NOT NULL,
    Alergenos VARCHAR(255),
    Precio DECIMAL(10, 2) NOT NULL,
    Existencias BOOLEAN NOT NULL
);

CREATE TABLE Mesas (
    id_mesa INT AUTO_INCREMENT PRIMARY KEY,
    Ubicacion VARCHAR(255) NOT NULL,
    Espacio_comensales INT NOT NULL
);

CREATE TABLE Cuentas (
    id_cuenta INT AUTO_INCREMENT PRIMARY KEY,
    FK1_id_pedidos INT,
    FK2_id_mesa INT,
    Precio_Total INT,
    Abierta BOOLEAN,
    FOREIGN KEY (FK2_id_mesa) REFERENCES Mesas (id_mesa)
);

CREATE TABLE Pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    fecha_pedido DATE NOT NULL,
    Precio INT NOT NULL,
    FK1_id_productos INT,
    FK2_id_cuenta INT,
    FK3_id_usuario INT,
    Notas VARCHAR(255),
    FOREIGN KEY (FK1_id_productos) REFERENCES Productos (id_producto),
    FOREIGN KEY (FK2_id_cuenta) REFERENCES Cuentas (id_cuenta),
    FOREIGN KEY (FK3_id_usuario) REFERENCES Usuarios (id_usuario)
);

CREATE TABLE Facturas (
    id_factura INT AUTO_INCREMENT PRIMARY KEY,
    FK1_id_cuenta INT,
    PRECIO_TOTAL INT,
    fecha_factura DATE NOT NULL,
    Problemas VARCHAR(255),
    FOREIGN KEY (FK1_id_cuenta) REFERENCES Cuentas (id_cuenta)
);

CREATE TABLE Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'camarero', 'cocina') NOT NULL,
    edad INT NOT NULL
);

CREATE TABLE Pedidos (
    id_pedido INT NOT NULL UNIQUE,
    fecha_pedido DATE NOT NULL,
    Precio INT NOT NULL,
    FK1_id_productos INT,
    FK2_id_cuenta INT,
    FK3_id_usuario INT,
    Notas VARCHAR(255),
    PRIMARY KEY (id_pedido)
);

-- Insertamos datos de ejemplo en cada tabla
INSERT INTO
    Productos (
        Nombre_Producto,
        Categoria,
        Alergenos,
        Precio,
        Existencias
    )
VALUES
    (
        'Coca Cola',
        'Bebida',
        'Ninguno',
        1.50,
        TRUE
    ),
    (
        'Pan de Ajo',
        'Entrante',
        'Gluten',
        3.50,
        TRUE
    ),
    (
        'Pizza Margarita',
        'Principal',
        'Gluten, Lactosa',
        7.99,
        TRUE
    );

INSERT INTO
    Mesas (Ubicacion, Espacio_comensales)
VALUES
    ('Terraza', 4),
    ('Salón', 2),
    ('Jardín', 6);

-- Los datos de la tabla 'Cuentas' dependen de los pedidos y las mesas
-- Asumiremos que se crean después de los pedidos y mesas
INSERT INTO
    Cuentas (
        FK1_id_pedidos,
        FK2_id_mesa,
        Precio_Total,
        Abierta
    )
VALUES
    (NULL, 1, 0, TRUE);

-- Cuenta inicial abierta sin pedidos
INSERT INTO
    Usuarios (
        nombre_usuario,
        nombre,
        apellidos,
        rol,
        edad
    )
VALUES
    (
        'admin1',
        'Juan',
        'Pérez',
        'admin',
        30
    ),
    (
        'camarero1',
        'Ana',
        'García',
        'camarero',
        25
    ),
    (
        'cocina1',
        'Carlos',
        'Sánchez',
        'cocina',
        45
    );

INSERT INTO
    Pedidos (
        id_pedido,
        fecha_pedido,
        Precio,
        FK1_id_productos,
        FK2_id_cuenta,
        FK3_id_usuario,
        Notas
    )
VALUES
    (1, '2024-04-22', 10, 1, 1, 1, 'Sin cebolla'),
    (2, '2024-04-21', 20, 2, 2, 2, 'Extra queso'),
    (3, '2024-04-20', 30, 3, 3, 3, 'Aderezo aparte');