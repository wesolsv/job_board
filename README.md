# job_board

API de um quadro de empregos, contendo empresa, pessoa, emprego e candidatura

Utilizado o banco de dados PostgreSQL para criar as tabelas, ao rodar o projeto o script para seed ocorre automaticamente.

Para instalar o projeto: mvn clean install

Para executar o projeto: mvn spring-boot:run

### Acesso local com o swagger: http://localhost:8080/swagger-ui.html#/

Neste projeto também implementei o SpringSecurity, tenho as entidades Role e User e nas configurações de segurança implemente o controle de usuários por Role.

Candidacy

GET    - api/v1/candidacy     - Retorna todas as candidaturas
GET    - api/v1/candidacy{id} - Retorna uma candidatura
POST   - api/v1/candidacy     - Cria nova candidatura
PUT    - api/v1/candidacy     - Altera uma candidatura
DELETE - api/v1/candidacy{id} - Deleta uma candidatura

Company
GET    - api/v1/company     - Retorna todas as empresas
GET    - api/v1/company{id} - Retorna uma empresa
POST   - api/v1/company     - Cria nova empresa
PUT    - api/v1/company     - Altera uma empresa
DELETE - api/v1/company{id} - Deleta uma empresa
GET    - api/v1/company/candidate/{id} - Retorna todos os candidatos de uma vaga
POST   - api/v1/company/candidate/hire - Contrata uma pessoa

Job
GET    - api/v1/job     - Retorna todas os empregos
GET    - api/v1/job{id} - Retorna um emprego
POST   - api/v1/job     - Cria novo emprego
PUT    - api/v1/job     - Altera um emprego
DELETE - api/v1/job{id} - Deleta um emprego

Person
GET    - api/v1/person     - Retorna todas as pessoas
GET    - api/v1/person{id} - Retorna uma pessoa
POST   - api/v1/person     - Cria nova pessoa
PUT    - api/v1/person     - Altera uma pessoa
DELETE - api/v1/person{id} - Deleta uma pessoa
GET    - api/v1/person/candidacy - Realiza uma nova candidatura 
POST   - api/v1/person/candidacy/{id} - Retorna todas as candidaturas da pessoa
