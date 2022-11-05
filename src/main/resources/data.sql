SET search_path TO job_board;

--PERSON
INSERT INTO person(name, phone, email, cpf, password)
	VALUES('Carolina Ana da Cruz', '68995966027', 'carolina@rafaelsouza.com.br','54748498692', '$2a$08$QP.Z/n7WxJ5.92JxowU7l.oD8IPAbu7EEVAEakWeQFn7P6yAAYXim');
INSERT INTO person(name, phone, email, cpf, password)
	VALUES('Bianca Juliana Ramos', '22986589348', 'bianca@hardquality.com.br','70513525980', '$2a$08$QP.Z/n7WxJ5.92JxowU7l.oD8IPAbu7EEVAEakWeQFn7P6yAAYXim');
INSERT INTO person(name, phone, email, cpf, password)
	VALUES('Benedito Thomas', '49998298745', 'beneditopires@deca.com.br','12096205379', '$2a$08$QP.Z/n7WxJ5.92JxowU7l.oD8IPAbu7EEVAEakWeQFn7P6yAAYXim');
INSERT INTO person(name, phone, email, cpf, password)
    VALUES('wes', '499123298745', 'wes@teste.com.br','12096105379', '$2a$08$sOOxkOE/arGYc6N1IBdzxO8kaWB7HWqlg/mhANhGeazRdDALX9vWK');

--COMPANY
INSERT INTO company(name, phone, email, cnpj, password)
	VALUES('Benício Informática Ltda', '3135824237', 'producao@leateltda.com.br','09028195000152','$2a$08$oFn06lNFvcMgqg4cU2E.IeT6v6/B/eDawhzRnmLGfmxZ21pQ5zlAW');
INSERT INTO company(name, phone, email, cnpj, password)
	VALUES('Laticinios Petter Parker', '38981133588', 'latprod@fojsc.br','05024237000140', '$2a$08$oFn06lNFvcMgqg4cU2E.IeT6v6/B/eDawhzRnmLGfmxZ21pQ5zlAW');
INSERT INTO company(name, phone, email, cnpj, password)
	VALUES('Vagas DEV', '38981943588', 'vagasti@vagasdev.com','05067937000140', '$2a$08$oFn06lNFvcMgqg4cU2E.IeT6v6/B/eDawhzRnmLGfmxZ21pQ5zlAW');

--ROLES
INSERT INTO roles(name) VALUES('USER');
INSERT INTO roles(name) VALUES('ADMIN');
INSERT INTO roles(name) VALUES('COMP');

--USERS
INSERT INTO users(email, password, person_id) VALUES('carolina@rafaelsouza.com.br', '$2a$08$QP.Z/n7WxJ5.92JxowU7l.oD8IPAbu7EEVAEakWeQFn7P6yAAYXim', 1);
INSERT INTO users(email, password, person_id) VALUES('bianca@hardquality.com.br', '$2a$08$QP.Z/n7WxJ5.92JxowU7l.oD8IPAbu7EEVAEakWeQFn7P6yAAYXim', 2);
INSERT INTO users(email, password, person_id) VALUES('beneditopires@deca.com.br', '$2a$08$QP.Z/n7WxJ5.92JxowU7l.oD8IPAbu7EEVAEakWeQFn7P6yAAYXim', 3);
INSERT INTO users(email, password, person_id) VALUES('wes@teste.com.br', '$2a$08$QP.Z/n7WxJ5.92JxowU7l.oD8IPAbu7EEVAEakWeQFn7P6yAAYXim', 4);
INSERT INTO users(email, password, company_id) VALUES('producao@leateltda.com.br', '$2a$08$oFn06lNFvcMgqg4cU2E.IeT6v6/B/eDawhzRnmLGfmxZ21pQ5zlAW', 1);
INSERT INTO users(email, password, company_id) VALUES('latprod@fojsc.br', '$2a$08$oFn06lNFvcMgqg4cU2E.IeT6v6/B/eDawhzRnmLGfmxZ21pQ5zlAW', 2);
INSERT INTO users(email, password, company_id) VALUES('vagasti@vagasdev.com', '$2a$08$oFn06lNFvcMgqg4cU2E.IeT6v6/B/eDawhzRnmLGfmxZ21pQ5zlAW', 3);

--USERS_ROLES
INSERT INTO users_roles(users_id, roles_id) VALUES(1,1);
INSERT INTO users_roles(users_id, roles_id) VALUES(2,1);
INSERT INTO users_roles(users_id, roles_id) VALUES(3,1);
INSERT INTO users_roles(users_id, roles_id) VALUES(4,2);
INSERT INTO users_roles(users_id, roles_id) VALUES(4,1);
INSERT INTO users_roles(users_id, roles_id) VALUES(5,3);
INSERT INTO users_roles(users_id, roles_id) VALUES(6,3);

--JOB
INSERT INTO job(opportunity, description, type, salary, benefits, status, person_id, company_id, date_publish)
	VALUES('TECNICO DE INFORMATICA','TECNICO DE INFORMATICA DESCRICAO', 'TEMPO INTEGRAL', 1500.00, 'VR VT CONVENIO MEDICO', 'OPEN', NULL, 1, '2022-11-01 12:00:00');
INSERT INTO job(opportunity, description, type, salary, benefits, status, person_id, company_id, date_publish)
	VALUES('ANALISTA DE SISTEMAS','ANALISE DE SISTEMAS DESCRICAO', 'TEMPO INTEGRAL', 2000.00, 'CONVENIO MEDICO', 'OPEN', NULL, 1, '2022-11-01 12:00:00');
INSERT INTO job(opportunity, description, type, salary, benefits, status, person_id, company_id, date_publish)
	VALUES('GERENTE COMERCIAL','GERENCIA DESCRICAO COMERCIAL', 'TEMPO INTEGRAL', 4000.00, 'VR VT CONVENIO MEDICO', 'COMPLETED', 1, 1, '2022-11-01 12:00:00');
INSERT INTO job(opportunity, description, type, salary, benefits, status, person_id, company_id, date_publish)
	VALUES('OPERADOR DE MAQUINA','OPERADOR DE MAQUINA DESCRIPTION', 'TEMPO INTEGRAL', 3500.00, 'VR VT CONVENIO MEDICO E ODONTOLOGICO', 'OPEN', NULL, 2, '2022-11-01 12:00:00');
INSERT INTO job(opportunity, description, type, salary, benefits, status, person_id, company_id, date_publish)
	VALUES('DESENVOLVEDOR BACK-END','DESENVOLVEDOR BACK END JAVA E PHP', 'TEMPO INTEGRAL', 3500.00, 'VR VT CONVENIO MEDICO E ODONTOLOGICO', 'OPEN', NULL, 3, '2022-11-01 12:00:00');
INSERT INTO job(opportunity, description, type, salary, benefits, status, person_id, company_id, date_publish)
	VALUES('DESENVOLVEDOR FRONT-END','DESENVOLVEDOR FRONT END JS, JAVA, PHP, HTML', 'TEMPO INTEGRAL', 3500.00, 'VR VT CONVENIO MEDICO E ODONTOLOGICO', 'OPEN', NULL, 3, '2022-11-01 12:00:00');
INSERT INTO job(opportunity, description, type, salary, benefits, status, person_id, company_id, date_publish)
	VALUES('ESTAGIARIO UI UX','ESTAGIO UI UX DESCRICAO', 'MEIO PERIODO', 3500.00, 'VR VT CONVENIO MEDICO E ODONTOLOGICO', 'OPEN', NULL, 3, '2022-11-01 12:00:00');

--CANDIDACY
INSERT INTO candidacy(date_candidacy, status, person_id, job_id)
	VALUES('2022-11-01 13:00:00', 'AGUARDANDO', 2, 1);