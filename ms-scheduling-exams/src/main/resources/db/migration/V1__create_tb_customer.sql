CREATE TABLE IF NOT EXISTS tb_customer (
    cod_customer UUID PRIMARY KEY,
    name VARCHAR(255),
    cpf VARCHAR(255),
    email VARCHAR(255),
    tel VARCHAR(255),
    cel VARCHAR(255),
    address VARCHAR(255),
    neighborhood VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    cep VARCHAR(255),
    date_insert DATE,
    date_update DATE
);