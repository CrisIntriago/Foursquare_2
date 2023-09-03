DROP DATABASE IF EXISTS Foursquare;
CREATE DATABASE IF NOT EXISTS Foursquare;

USE Foursquare;

-- TABLAS

CREATE TABLE IF NOT EXISTS Tipo(
id_Tipo int AUTO_INCREMENT PRIMARY KEY,
nombre varchar(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS Sitio(
id_Sitio int,
nombre varchar(50),
id_Tipo int NOT NULL,
precio_min DECIMAL(4,2), -- permite nulos
precio_max DECIMAL(4,2), -- permite nulos
disponibilidad varchar(50),
id_Ubicacion int,
FOREIGN KEY (id_Tipo) REFERENCES Tipo(id_Tipo)
);

CREATE TABLE IF NOT EXISTS Ofrece(
id_Servicio int,
id_Sitio int
);

CREATE TABLE IF NOT EXISTS Servicio(
id_Servicio int,
nombre varchar(50),
descripcion varchar(50) -- Permite nulos
);

CREATE TABLE IF NOT EXISTS Pertenencia(
id_Pertenece int,
id_Usuario int,
id_Sitio int
);

CREATE TABLE IF NOT EXISTS Reclamo(
id_Reclamo int,
fecha datetime,
id_Usuario int,
id_Pertenece int
);

CREATE TABLE IF NOT EXISTS Resena(
id_Resena int,
descripcion TEXT,
id_Sitio int,
id_Usuario int
);

CREATE TABLE IF NOT EXISTS Calificacion(
id_Calificacion int,
numEstrellas int,
id_Sitio int,
id_Usuario int
);

CREATE TABLE IF NOT EXISTS Usuario (
id_Usuario int,
correo varchar(30),
nombre varchar(20),
apellido varchar(20), -- permite nulos
biografia varchar(100), -- permite nulos
telefono char(10), -- permite nulos
id_Ubicacion int, -- permite nulos
contrasena varchar(16),
sexo varchar(25),
fechaNacimiento date,
id_Foto int -- permite nulos
);

CREATE TABLE IF NOT EXISTS Lista(
id_Lista int,
id_Usuario int,
nombre varchar(64),
descripcion varchar(200), -- permite nulos
fechaCreacion Date
);

CREATE TABLE IF NOT EXISTS Foto(
id_Foto int,
ruta varchar(50),
id_Resena int -- permite nulos (cuando es una foto de perfil)
);

CREATE TABLE IF NOT EXISTS Tiene(
id_Lista int,
id_Sitio int
);

CREATE TABLE IF NOT EXISTS Ubicacion(
id_Ubicacion int, -- PK
codigoPostal varchar(12), -- no permite nulos
calleTransversal varchar(64), -- no permite nulos
id_Parroquia int -- FK
);

CREATE TABLE IF NOT EXISTS Parroquia(
id_Parroquia int, -- PK
parroquia_nom varchar(32), -- no permite nulos
id_Ciudad int -- FK
);

CREATE TABLE IF NOT EXISTS Ciudad(
id_Ciudad int, -- PK
ciudad_nom varchar(32), -- no permite nulos
id_Pais int
);

CREATE TABLE IF NOT EXISTS Pais(
id_Pais int,
pais_nom varchar(20) -- no permite nulos
);

-- CONSTRAINTS

ALTER TABLE Tiene MODIFY id_Lista int NOT NULL;
ALTER TABLE Tiene MODIFY id_Sitio int NOT NULL;

ALTER TABLE Pertenencia MODIFY id_Sitio int NOT NULL UNIQUE; -- Solo una persona puede tener un mismo sitio
ALTER TABLE Pertenencia MODIFY id_Usuario int NOT NULL;

ALTER TABLE Reclamo MODIFY fecha datetime DEFAULT NOW() NOT NULL;
ALTER TABLE Reclamo MODIFY id_Usuario int NOT NULL;
ALTER TABLE Reclamo MODIFY id_Pertenece int NOT NULL;

ALTER TABLE Resena MODIFY descripcion text NOT NULL;
ALTER TABLE Resena MODIFY id_Sitio int NOT NULL;
ALTER TABLE Resena MODIFY id_Usuario int NOT NULL;

ALTER TABLE Calificacion MODIFY numEstrellas int NOT NULL;
ALTER TABLE Calificacion MODIFY id_Sitio int NOT NULL;
ALTER TABLE Calificacion MODIFY id_Usuario int NOT NULL;

ALTER TABLE Sitio MODIFY nombre VARCHAR(50) NOT NULL;
ALTER TABLE Sitio MODIFY disponibilidad VARCHAR(50) NOT NULL;
ALTER TABLE Sitio MODIFY id_Ubicacion int NOT NULL;

ALTER TABLE Servicio MODIFY nombre VARCHAR(50) NOT NULL;

ALTER TABLE Ofrece MODIFY id_Sitio int NOT NULL;
ALTER TABLE Ofrece MODIFY id_Servicio int NOT NULL;

ALTER TABLE Usuario MODIFY correo varchar(30) NOT NULL;
ALTER TABLE Usuario MODIFY nombre varchar(20) NOT NULL;
ALTER TABLE Usuario MODIFY contrasena varchar(16) NOT NULL;
ALTER TABLE Usuario MODIFY sexo varchar(25) NOT NULL;
ALTER TABLE Usuario MODIFY fechaNacimiento date NOT NULL;

ALTER TABLE Lista MODIFY id_Usuario int NOT NULL;
ALTER TABLE Lista MODIFY nombre varchar(64) NOT NULL;
ALTER TABLE Lista MODIFY fechaCreacion Date DEFAULT (DATE(NOW()));

ALTER TABLE Foto MODIFY ruta varchar(50) NOT NULL;

ALTER TABLE Ubicacion MODIFY codigoPostal varchar(12) NOT NULL;
ALTER TABLE Ubicacion MODIFY calleTransversal varchar(64) NOT NULL;
ALTER TABLE Ubicacion MODIFY id_Parroquia int NOT NULL;

ALTER TABLE Parroquia MODIFY parroquia_nom varchar(32) NOT NULL;
ALTER TABLE Parroquia MODIFY id_Ciudad int NOT NULL;

ALTER TABLE Ciudad MODIFY ciudad_nom varchar(32) NOT NULL;
ALTER TABLE Ciudad MODIFY id_Pais int NOT NULL;

ALTER TABLE Pais MODIFY pais_nom varchar(20) NOT NULL;

-- PRIMARY KEYS

ALTER TABLE Tiene ADD CONSTRAINT PK_Tiene PRIMARY KEY (id_Lista,id_Sitio);

ALTER TABLE Pertenencia MODIFY id_Pertenece int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Reclamo MODIFY id_Reclamo int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Resena MODIFY id_Resena int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Calificacion MODIFY id_Calificacion int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Sitio MODIFY id_Sitio int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Servicio MODIFY id_Servicio int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Ofrece ADD CONSTRAINT PK_Ofrece PRIMARY KEY (id_Sitio, id_Servicio);

ALTER TABLE Foto MODIFY id_Foto int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Usuario MODIFY id_Usuario int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Lista MODIFY id_Lista int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Ubicacion MODIFY id_Ubicacion int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Parroquia MODIFY id_Parroquia int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Ciudad MODIFY id_Ciudad int AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE Pais MODIFY id_Pais int AUTO_INCREMENT PRIMARY KEY;

-- FOREIGN KEYS

ALTER TABLE Tiene ADD CONSTRAINT FK_Tiene_Lista FOREIGN KEY (id_Lista) REFERENCES Lista (id_Lista);
ALTER TABLE Tiene ADD CONSTRAINT FK_Tiene_Sitio FOREIGN KEY (id_Sitio) REFERENCES Sitio (id_Sitio);

ALTER TABLE Pertenencia ADD CONSTRAINT FK_Pertenencia_Sitio FOREIGN KEY (id_Sitio) REFERENCES Sitio (id_Sitio);
ALTER TABLE Pertenencia ADD CONSTRAINT FK_Pertenencia_Usuario FOREIGN KEY (id_Usuario) REFERENCES Usuario (id_Usuario);

ALTER TABLE Reclamo ADD CONSTRAINT FK_Reclamo_Usuario FOREIGN KEY (id_Usuario) REFERENCES Usuario (id_Usuario);
ALTER TABLE Reclamo ADD CONSTRAINT FK_Reclamo_Pertenece FOREIGN KEY (id_Pertenece) REFERENCES Pertenencia (id_Pertenece);

ALTER TABLE Resena ADD CONSTRAINT FK_Resena_Sitio FOREIGN KEY (id_Sitio) REFERENCES Sitio (id_Sitio);
ALTER TABLE Resena ADD CONSTRAINT FK_Resena_Usuario FOREIGN KEY (id_Usuario) REFERENCES Usuario (id_Usuario);

ALTER TABLE Calificacion ADD CONSTRAINT FK_Calificacion_Sitio FOREIGN KEY (id_Sitio) REFERENCES Sitio (id_Sitio);
ALTER TABLE Calificacion ADD CONSTRAINT FK_Calificacion_Usuario FOREIGN KEY (id_Usuario) REFERENCES Usuario (id_Usuario);

ALTER TABLE Sitio ADD FOREIGN KEY (id_Ubicacion) REFERENCES Ubicacion(id_Ubicacion);

ALTER TABLE Ofrece ADD FOREIGN KEY (id_Sitio) REFERENCES Sitio(id_Sitio);
ALTER TABLE Ofrece ADD FOREIGN KEY (id_Servicio) REFERENCES Servicio(id_Servicio);

ALTER TABLE Lista ADD FOREIGN KEY (id_Usuario) REFERENCES Usuario(id_Usuario);

ALTER TABLE Usuario ADD FOREIGN KEY (id_Ubicacion) REFERENCES Ubicacion(id_Ubicacion);
ALTER TABLE Usuario ADD FOREIGN KEY (id_Foto) REFERENCES Foto(id_Foto);

ALTER TABLE Foto ADD FOREIGN KEY (id_Resena) REFERENCES Resena(id_Resena);

ALTER TABLE Ubicacion ADD FOREIGN KEY (id_Parroquia) REFERENCES Parroquia(id_Parroquia);

ALTER TABLE Parroquia ADD FOREIGN KEY (id_Ciudad) REFERENCES Ciudad(id_Ciudad);

ALTER TABLE Ciudad ADD FOREIGN KEY (id_Pais) REFERENCES Pais(id_Pais);

-- INSERTS

INSERT INTO Pais VALUES
(1,'Ecuador'),
(2,'Perú'),
(3,'Chile'),
(4,'Colombia'),
(5,'Argentina');

INSERT INTO Ciudad (ciudad_nom, id_Pais) VALUES
('Guayaquil',1), ('Samborondón',1), ('Manta',1), ('Quito',1), ('Cuenca',1),
('Lima',2), ('Arequipa',2), ('Trujillo',2), ('Chiclayo',2), ('Piura',2),
('Santiago de Chile',3), ('Valparaíso',3), ('Concepción',3), ('La Serena',3), ('Antofagasta',3),
('Bogotá',4), ('Medellín',4), ('Cali',4), ('Barranquilla',4), ('Cartagena de Indias',4),
('Buenos Aires',5), ('Córdoba',5), ('Rosario',5), ('Mendoza',5), ('San Miguel de Tucumán',5);

INSERT INTO Parroquia (parroquia_nom, id_Ciudad) VALUES
('Tarqui',1), ('Urbina Jado',1), ('Samborondón',2), ('La Puntilla',2), ('Manta',3),
('San Mateo',3), ('La Magdalena',4), ('San Roque',4), ('San Blas',5), ('El Sagrario',5),
('San Isidro',6), ('Miraflores',6), ('Cayma',7), ('Cerro Colorado',7), ('El Porvenir',8),
('La Esperanza',8), ('La Victoria',9), ('José Leonardo Ortiz',9), ('Castilla',10), ('Piura',10),
('Providencia',11), ('Las Condes',11), ('Valparaíso',12), ('Viña del Mar',12), ('Concepción',13),
('Talcahuano',13), ('La Serena',14), ('Coquimbo',14), ('Antofagasta',15), ('Calama',15),
('Chapinero',16), ('Usaquén',16), ('El Poblado',17), ('La Candelaria',17), ('Cali',18),
('Aguablanca',18), ('Metropolitana',19), ('Suroriente',19), ('Cartagena de Indias',20), ('Turbaco',20),
('Palermo',21), ('Recoleta',21), ('Córdoba',22), ('Río Cuarto',22), ('Rosario',23),
('Santa Fe',23), ('Mendoza',24), ('Godoy Cruz',24), ('San Miguel de Tucumán',25), ('Yerba Buena',25);

INSERT INTO Ubicacion (codigoPostal, calleTransversal, id_Parroquia) VALUES
(092301,'Av. Rio Esmeraldas',2),
(090150,'Av. Juan Tanca Marengo',1),
(090150,'Pa. de España',1),
(090401,'Cnel. Miguel de Letamendi y Cnel. Manuel Torres Valdivia',3),
(090116,'Av. Machachi',4),
(0106,'Santa Isabel',21);

INSERT INTO Tipo VALUES
(1,'Comida'),
(2,'Café'),
(3,'Nocturna'),
(4,'Diversión'),
(5,'Compras');

INSERT INTO Sitio VALUES
(1,'Teatro Sanchez Aguilar',3,null,null,'Abierto',1),
(2,'La Casa Di Carlo',1,5.00,30.00,'Abierto',2),
(3,'Puerto Santa Ana',3,null,null,'Abierto',3),
(4,'Noe Sushi Bar',1,5.00,30.00,'Abierto',4),
(5,'La Pepa de Oro',1,0.40,50.00,'Abierto',5),
(6,'Lala Leelu',2,null,null,'Abierto',6);

INSERT INTO Servicio (nombre, descripcion) VALUES
('Tarjetas de crédito','Todo tipo de tarjeta'),
('Reservas',null),
('Wi-Fi',null),
('Mesas al aire libre',null),
('Música','Música en vivo');

INSERT INTO Ofrece VALUES
(1,1),
(2,1),
(2,2),
(3,1),
(5,4);

INSERT INTO Foto(ruta) VALUES
('assets/imgs/perfiles/foto1.jpg'),
('assets/imgs/perfiles/foto_perfil.jpeg');

INSERT INTO Usuario (correo,nombre,biografia,telefono,contrasena,sexo,fechaNacimiento,id_Foto) VALUES
('usuario1@example.com', 'Ariana', 'Amante de la naturaleza y la aventura.', null, 'contrasena123', 'Femenino', '1990-05-15', 1),
('usuario2@example.com', 'Alexander', null, null, 'passw0rd', 'Masculino', '1985-11-30', 2),
('usuario3@example.com', 'Cristian', 'Viajero empedernido y amante de la gastronomía.', '5555555555', 'secreto123', 'Masculino', '1992-08-22', null),
('usuario4@example.com', 'Dario', null, '5551112222', 'password123', 'Prefiero no decirlo', '1998-02-10', null),
('usuario5@example.com', 'Jean', 'Lector ávido y cinéfilo apasionado.', '5554443333', 'claveSegura', 'Masculino', '1994-07-05', null);

INSERT INTO Resena (descripcion, id_Sitio, id_Usuario) VALUES
('Es de los mejores restaurantes gourmet de Guayaquil!!',5,1),
('La mejor atención gourmet y el sushi es espectacular',4,1),
('Los postres en este lugar son deliciosos, pero le falta mejorar atención al cliente',2,2),
('Es un lugar inmenso, deberían aprovechar mejor el espacio...',1,3),
('Está bonito',1,2),
('No me gustó la atención',1,5),
('Muy buena vista al río, pero los restaurantes son muy caros :)',3,4);

INSERT INTO Foto(ruta,id_Resena) VALUES
('assets/imgs/resenas/teatroVisita.png', 4),
('assets/imgs/resenas/imagen.jpg', 2),
('assets/imgs/resenas/rest_gourmet.jpg', 1);

INSERT INTO Calificacion (numEstrellas,id_Sitio,id_Usuario) VALUES
(5,5,1),
(5,4,1),
(3,2,2),
(1,1,3),
(2,3,4);

INSERT INTO Lista (id_usuario,nombre,descripcion,fechaCreacion) VALUES
(1, 'Restaurantes Gourmet', null,'20230728'),
(2, 'Los mejores lugares de Postres', 'Hola comunidad, aqui podemos añadir los lugares a su alrededor que recomienden visitar para comer postres.', '2023-07-27'),
(3, 'Mis teatros favoritos', null, '2023-07-26');
INSERT INTO Lista (id_usuario,nombre) VALUES
(1, 'Lista de Comida Internacional'),
(5, 'Lista de Food Trucks Favoritos');

INSERT INTO Tiene VALUES
(1,5),
(1,4),
(2,2),
(3,1),
(4,4);

INSERT INTO Pertenencia (id_Usuario,id_Sitio) VALUES
(1,4),
(1,5),
(2,3),
(5,1),
(4,2);

INSERT INTO Reclamo (fecha,id_Usuario,id_Pertenece) VALUES
(NOW(),3,4),
('2023-07-01 10:23:11',1,1),
('2023-05-02 16:23:21',1,2),
(NOW(),4,3),
('2022-01-01 19:02:00',5,5);