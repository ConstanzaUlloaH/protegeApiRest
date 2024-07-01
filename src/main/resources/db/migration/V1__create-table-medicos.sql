CREATE TABLE medicos (
    id BIGSERIAL NOT NULL ,
    nombre varchar(100) NOT NULL,
    email varchar(100) NOT NULL UNIQUE,
    documento varchar(9) NOT NULL UNIQUE,
    especialidad varchar(100) NOT NULL,
    calle varchar(100) NOT NULL,
    comuna varchar(100) NOT NULL,
    numero varchar(20) NOT NULL,
    ciudad varchar(100) NOT NULL,
    complemento varchar(100) NOT NULL,
    PRIMARY KEY (id)
);
