CREATE TABLE atletas (
	licencia varchar(10) NOT NULL UNIQUE,
    nombre varchar(50) NOT NULL,
    apellidos varchar(100) NOT NULL,
    fecha_nacimiento date,
    ciudad varchar(50) NOT NULL,
	telefono varchar(10),
	email varchar(100),
	sexo varchar(100) NOT NULL,
	categoria varchar(100) NOT NULL,
	PRIMARY KEY atleta_pk(licencia)
);

CREATE TABLE resultado (
	id_resultado int NOT NULL AUTO_INCREMENT,
	licencia varchar(10) NOT NULL,
	prueba varchar(50) NOT NULL,
	marca varchar(50) NOT NULL,
	fecha date NOT NULL,
	lugar varchar(50) NOT NULL,
	PRIMARY KEY resultado_pk(id_resultado)
)