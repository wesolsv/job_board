SET search_path TO job_board;

--PERSON
INSERT INTO person(name, phone, email, cpf, password, roles)
	VALUES('Carolina Ana da Cruz', '68995966027', 'carolina.ana.dacruz@rafaelsouza.com.br','54748498692', '123456', 'USER ADMIN');
INSERT INTO person(name, phone, email, cpf, password, roles)
	VALUES('Bianca Juliana Ramos', '22986589348', 'bianca.juliana.ramos@hardquality.com.br','70513525980', '1234567' 'USER');
INSERT INTO person(name, phone, email, cpf, password, roles)
	VALUES('Benedito Thomas Fábio Pires', '49998298745', 'benedito_pires@deca.com.br','12096205379', '12345678' 'USER');

--COMPANY
INSERT INTO company(name, phone, email, cnpj)
	VALUES('Benício Informática Ltda', '3135824237', 'producao@leandroemaiterestauranteltda.com.br','09028195000152');
INSERT INTO company(name, phone, email, cnpj)
	VALUES('Laticinios Petter Parker', '38981133588', 'latprod@fojsc.br','05024237000140');

--JOB
INSERT INTO job(opportunity, description, type, salary, benefits, status, person_id, company_id, date_publish)
	VALUES('Tecnico de Informatica','Tecnico de Informatica description', 'Tempo Integral', 1500.00, 'VR VT CONVENIO MEDICO', 'OPEN', NULL, 1, '2022-11-01 12:00:00');
INSERT INTO job(opportunity, description, type, salary, benefits, status, person_id, company_id, date_publish)
	VALUES('Analista de Sistemas','Analista de sistemas description', 'Tempo Integral', 2000.00, 'CONVENIO MEDICO', 'OPEN', NULL, 1, '2022-11-01 12:00:00');
INSERT INTO job(opportunity, description, type, salary, benefits, status, person_id, company_id, date_publish)
	VALUES('Gerente','Gerente description', 'Tempo Integral', 4000.00, 'VR VT CONVENIO MEDICO', 'COMPLETED', 1, 1, '2022-11-01 12:00:00');
INSERT INTO job(opportunity, description, type, salary, benefits, status, person_id, company_id, date_publish)
	VALUES('Operador de Maquina','Operador de maquina description', 'Tempo Integral', 3500.00, 'VR VT CONVENIO MEDICO E ODONTOLOGICO', 'OPEN', NULL, 1, '2022-11-01 12:00:00');

--CANDIDACY
INSERT INTO candidacy(date_candidacy, status, person_id, job_id)
	VALUES('2022-11-01 13:00:00', 'AGUARDANDO', 2, 1);