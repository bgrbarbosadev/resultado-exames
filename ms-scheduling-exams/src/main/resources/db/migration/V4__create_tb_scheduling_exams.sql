CREATE TABLE IF NOT EXISTS tb_scheduling_exams (
    scheduling_uuid UUID,
    exam_uuid UUID,
    PRIMARY KEY (scheduling_uuid, exam_uuid),
    FOREIGN KEY (scheduling_uuid) REFERENCES tb_scheduling (cod_scheduling),
    FOREIGN KEY (exam_uuid) REFERENCES tb_exam (cod_exam)
);