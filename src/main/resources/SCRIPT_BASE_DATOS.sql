/* se escribio para mysql. Cambiar nombre en USE por el nombre que corresponda */
/*USE my_local_database; */
CREATE DATABASE my_local_database;
USE my_local_database;
CREATE TABLE Tarea (
	identificador int NOT NULL AUTO_INCREMENT,
    descripcion varchar(500) NOT NULL,
    fecha_creacion datetime NOT NULL,
    vigente boolean NOT NULL,
    PRIMARY KEY (identificador)
);