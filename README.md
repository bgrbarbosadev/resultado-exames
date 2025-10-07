# SISTEMA DE AGENDAMENTO E RESULTADO DE EXAMES - API
<br>
Sistema projetado para o controle de agendamento e resultado de exames laboratoriais. <br><br>
Este Sistema é composto por 8 microservices da um com a sua funcionalidade descrita no tópico Módulos e Funcionalidades.<br><br>
O objetivo da API é explorar os recursos do desenvolvimento de Microservices com RabbitMQ e padrão de autenticação com Oauth2, estando o projeto aberto para acrescentar novos recursos futuramente.

---
# Módulos e Funcionalidades:

    . ms-customer: Responsável pelo controle de clientes. Nele possuímos um crud onde temos as operações de: Insert, busca, Update e Delete.
    . ms-exame: Responsável pelo controle de exames. Nele possuímos um crud onde temos as operações de: Insert, busca, Update e Delete. 
    . ms-scheduling: Responsável pelo controle dos agendamento dos exames. Nele possuímos um crud onde temos as operações de: Insert, busca, Update e Delete.
    . ms-user: Responsável pelo controle de usuários a operar o sistema. Nele possuímos um crud onde temos as operações de: Insert, busca, Update e Delete.
    . ms-oauth: Responsável pela autenticação dos usuários no sistema;
    . ms-registry: Reponsável pelo registros dos microservices em uma pattern de Service Registry
    . ms-gateway: Responsável por rotear as requisições do demais microservices, nele também possuímos a funcionalidade de Resources Services para controle de quem pode acessar o que
    . ms-notification: Responsável por notificar os clientes que os seus exames já estão liberados para acessar seus laudos.

---
## 🚀 Tecnologias Utilizadas

Linguagem: Java - versão 21 <br>
Framework: Spring Boot <br>
Banco de Dados: Postgres <br>
Containerização: Docker <br>
Mensageria: Utilizando RabbitMQ <br>
Storage: AWS Bucket S3 <br>
Padrão de Autenticação e Autorização: Oauth2 <br>
Notificações via email: Serviço de envio do gmail <br>
Migration: Flyway

---
## 🛠️ Montagem do Ambiente com Docker

Para facilitar a configuração e garantir um ambiente de desenvolvimento consistente, utilizamos o Docker. Siga estes passos para subir a aplicação:

### Clone o repositório com o comando

1° - git clone https://github.com/bgrbarbosadev/resultado-exames.git

2° - entre no diretório do projeto (resultado-exames)

3° - Construa e inicie os contêineres através do git bash com o comando abaixo: **docker-compose up -d**

4° - Configure as seguintes variáveis de ambiente em um arquivo .env para setar as configurações a serem utilizadas pelos microservices

    AWS_KEY_ACCESS = Chave de acesso da conta AWS
    AWS_SECRET_KEY = Secret key da conta AWS
    AWS_REGION = Região da AWS onde a aplicação está hospedada
    AWS_BUCKET = Nome do BUCKET S3 criado na AWS para a funcionalidade de storage da aplicação
    HOST_MAIL = Host do serviço de email da gmail da conta utilizada pelo sistema (smtp.gmail.com)
    PORT = Porta do serviço SMTP para envio dos emails pela aplicação - normalmente (587)
    HOST_USERNAME = Username da conta gmail utilizada
    HOST_PASSWORD = Senha de APP criada na conta gmail utilizada pelo sistema

### Criar uma conexão com o banco e rodar o sql abaixo para o insert do usuário padrão para o inicio das atividades:

### Dados da conexão:

DATASOURCE_URL=jdbc:postgresql://user-db:5433/postgres <br>
DATASOURCE_USERNAME=postgres <br>
DATASOURCE_PASSWORD=123456

### SQL:

INSERT INTO tb_role("uuid", authority)VALUES('55f46b07-1c87-4bb4-b67f-ccbeaa36b631'::uuid, 'ROLE_ADMIN');
INSERT INTO tb_role("uuid", authority)VALUES('9cfae039-7c5f-4369-97b2-883b68e2c031'::uuid, 'ROLE_USER');

INSERT INTO tb_user("uuid", email, first_name, last_name, "password")
VALUES('c2347cd4-53f0-4555-84f5-3c78763da59b'::uuid, 'admin@gmail.com', 'admin', 'admin', '$2a$10$fAdC5xIHW5/uCJfHpHEA/.N4P3jQ7ivnssz.Y0pLvCzyF8VT0NRwS');

INSERT INTO tb_user("uuid", email, first_name, last_name, "password")
VALUES('ea58c7c6-f3c7-41e3-bddd-f001512220db'::uuid, 'user@gmail.com', 'user', 'user', '$2a$10$JJBfgNfKE2SxqyEbko/gO.SmSGCS0Z7uW3HkAXEYtFu4zf0Kid1Ri');

INSERT INTO tb_user_role(user_id, role_id)VALUES(c2347cd4-53f0-4555-84f5-3c78763da59b, 55f46b07-1c87-4bb4-b67f-ccbeaa36b631);
INSERT INTO tb_user_role(user_id, role_id)VALUES(ea58c7c6-f3c7-41e3-bddd-f001512220db, 9cfae039-7c5f-4369-97b2-883b68e2c031);

### Acesse a API
Após os contêineres estarem rodando, a API estará disponível.
<br>Consulte a seção de "Recursos" para ver as coleções de endpoints.

---
## 💡 Ajuda.

#### As collections utilizadas na api vocês encontram dentro do diretório raiz do projeto - Sistema de Resultado de Exames.postman_collection.json
#### Documentação da API (Swagger/OpenAPI): http://localhost:8080/swagger-ui/index.html#/


### **&copy; 2025 Bruno Gaspar Romeiro Barbosa - Email: bgrbarbosa@hotmail.com**

