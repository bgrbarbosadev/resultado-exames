CREATE TABLE IF NOT EXISTS tb_customer (
    cod_customer UUID PRIMARY KEY,
    name VARCHAR(255),
    cpf VARCHAR(14),
    email VARCHAR(255),
    tel VARCHAR(20),
    cel VARCHAR(20),
    address VARCHAR(255),
    neighborhood VARCHAR(100),
    city VARCHAR(100),
    state VARCHAR(2),
    cep VARCHAR(9),
    date_insert DATE,
    date_update DATE
);