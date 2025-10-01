CREATE TABLE IF NOT EXISTS tb_exam (
    cod_exam UUID PRIMARY KEY,
    desc_exam VARCHAR(255),
    vl_exame DOUBLE PRECISION,
    orientation_exam VARCHAR(255)
);