CREATE TABLE IF NOT EXISTS tb_scheduling (
    cod_scheduling UUID PRIMARY KEY,
    status VARCHAR(255),
    vl_exam DOUBLE PRECISION,
    protocol BIGINT,
    password VARCHAR(255),
    date_exam DATE,
    date_result DATE,
    customer_uuid UUID,
    FOREIGN KEY (customer_uuid) REFERENCES tb_customer (cod_customer)
);
