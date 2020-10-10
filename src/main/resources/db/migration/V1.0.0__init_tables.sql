CREATE TABLE atletas (
	atleta_id int NOT NULL AUTO_INCREMENT,
	licencia varchar(10) NOT NULL UNIQUE,
    nombre varchar(50) NOT NULL,
    apellidos varchar(100) NOT NULL,
    fecha_nacimiento date NOT NULL,
    ciudad varchar(50) NOT NULL,
	telefono varchar(10),
	email varchar(100),
	sexo varchar(100) NOT NULL,
	categoria varchar(100) NOT NULL,
	PRIMARY KEY licencia_pk(atleta_id)
);

CREATE TABLE resultado (
	resultado_id int NOT NULL AUTO_INCREMENT,
	licencia varchar(10) NOT NULL,
	prueba varchar(50) NOT NULL,
	marca varchar(50) NOT NULL,
	fecha date NOT NULL,
	lugar varchar(50) NOT NULL,
	PRIMARY KEY id_licencia_pk(resultado_id)
)