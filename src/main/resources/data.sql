SET search_path TO job_board;

--PERSON
INSERT INTO person(name, phone, email, cpf)
	VALUES('Carolina Ana da Cruz', '68995966027', 'carolina.ana.dacruz@rafaelsouza.com.br','54748498692');
INSERT INTO person(name, phone, email, cpf)
	VALUES('Bianca Juliana Ramos', '22986589348', 'bianca.juliana.ramos@hardquality.com.br','70513525980');
INSERT INTO person(name, phone, email, cpf)
	VALUES('Benedito Thomas Fábio Pires', '49998298745', 'benedito_pires@deca.com.br','12096205379');

--COMPANY
INSERT INTO company(name, phone, email, cnpj)
	VALUES('Benício Informática Ltda', '3135824237', 'producao@leandroemaiterestauranteltda.com.br','09028195000152');
INSERT INTO company(name, phone, email, cnpj)
	VALUES('Laticinios Petter Parker', '38981133588', 'latprod@fojsc.br','05024237000140');

--JOB
INSERT INTO job(opportunity, type, salary, benefits, status, person_id, company_id)
	VALUES('Tecnico de Informatica', 'Tempo Integral', 1500.00, 'VR VT CONVENIO MEDICO', 'OPEN', NULL, 1);
INSERT INTO job(opportunity, type, salary, benefits, status, person_id, company_id)
	VALUES('Analista de Sistemas', 'Tempo Integral', 2000.00, 'VR VT CONVENIO MEDICO', 'OPEN', NULL, 1);
INSERT INTO job(opportunity, type, salary, benefits, status, person_id, company_id)
	VALUES('Gerente', 'Tempo Integral', 4000.00, 'VR VT CONVENIO MEDICO', 'COMPLETED', 1, 1);
INSERT INTO job(opportunity, type, salary, benefits, status, person_id, company_id)
	VALUES('Operador de Maquina', 'Tempo Integral', 3500.00, 'VR VT CONVENIO MEDICO E ODONTOLOGICO', 'OPEN', NULL, 1);

--JOB_PUBLISHED
INSERT INTO job_published(description, date_publish, job_id)
	VALUES('Tecnico de Informatica description', '2022-11-01 12:00:00', 1);

INSERT INTO job_published(description, date_publish, job_id)
	VALUES('Analista de Sistemas description', '2022-11-01 12:00:00', 2);

--CANDIDACY
INSERT INTO candidacy(date_candidacy, status, person_id, job_published_id)
	VALUES('2022-11-01 13:00:00', 'AGUARDANDO', 2, 1);