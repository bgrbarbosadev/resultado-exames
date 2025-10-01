CREATE TABLE IF NOT EXISTS tb_exam (
    cod_exam UUID PRIMARY KEY,
    desc_exam VARCHAR(255) NOT NULL,
    vl_exame DECIMAL(10,2),
    orientation_exam VARCHAR(255),
    cod_exam_type UUID,
    CONSTRAINT fk_exam_exam_type FOREIGN KEY (cod_exam_type) REFERENCES tb_exam_type (cod_exam_type)
);