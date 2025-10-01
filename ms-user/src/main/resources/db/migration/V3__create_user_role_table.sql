-- 3. Criação da Tabela de Associação tb_user_role
CREATE TABLE IF NOT EXISTS tb_user_role (
    id_user BIGINT NOT NULL,
    id_role BIGINT NOT NULL,
    PRIMARY KEY (id_user, id_role),
    FOREIGN KEY (id_user) REFERENCES tb_user (id_user) ON DELETE CASCADE,
    FOREIGN KEY (id_role) REFERENCES tb_role (id_role) ON DELETE CASCADE
);