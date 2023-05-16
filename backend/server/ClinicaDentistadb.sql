DROP DATABASE IF EXISTS ClinicaDentistadb;
CREATE DATABASE ClinicaDentistadb;
USE ClinicaDentistadb;

CREATE TABLE Pacientes (
    DNI VARCHAR(9) NOT NULL PRIMARY KEY,
    Nombre VARCHAR(15),
    Apellidos VARCHAR(15),
    Telefono VARCHAR(9),
    Fnac DATE,
    Email VARCHAR(40) 
);

CREATE TABLE Tratamiento (
    Codigo INT AUTO_INCREMENT PRIMARY KEY,
    Descripcion VARCHAR(100),
    Fecha DATE,
    Precio FLOAT,
    Cobrado BOOLEAN DEFAULT FALSE,
    Dni_Paciente VARCHAR(9) NOT NULL,
    FOREIGN KEY (Dni_Paciente) REFERENCES Pacientes (DNI)
);

INSERT INTO Pacientes (DNI, Nombre, Apellidos, Telefono, Fnac, Email) VALUES ('123456789', 'Lorenita', 'idk', '123456789', '2003-01-01', 'Lorenita@suCorresito.com');
INSERT INTO Pacientes (DNI, Nombre, Apellidos, Telefono, Fnac, Email) VALUES ('987654321', 'Nikola', 'Tesla', '987654321', '2002-10-30', 'nikolito@tesla.com');

INSERT INTO Tratamiento (Descripcion, Fecha, Precio, Cobrado, Dni_Paciente) VALUES ('Limpieza dental', '2023-05-01', 50.0, FALSE, '123456789');
INSERT INTO Tratamiento (Descripcion, Fecha, Precio, Cobrado, Dni_Paciente) VALUES ('Extracción de muela', '2023-05-10', 80.0, TRUE, '987654321');

