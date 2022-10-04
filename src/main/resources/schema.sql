DROP SCHEMA job_board CASCADE;

CREATE SCHEMA IF NOT EXISTS job_board AUTHORIZATION postgres;

SET search_path TO job_board;

CREATE TABLE IF NOT EXISTS person(
	id INT GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(150) NOT NULL,
	phone VARCHAR(15) UNIQUE NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	cpf  VARCHAR(11) UNIQUE NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS company(
	id INT GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(150) NOT NULL,
	phone VARCHAR(15) UNIQUE NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	cnpj  VARCHAR(14) UNIQUE NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS job(
	id INT GENERATED ALWAYS AS IDENTITY,
	opportunity VARCHAR(50) NOT NULL,
	type VARCHAR(20) NOT NULL,
	salary DECIMAL NOT NULL,
	benefits  VARCHAR(50) NOT NULL,
	status VARCHAR(20) NOT NULL,
	person_id INT UNIQUE NULL,
	company_id INT NULL,
	PRIMARY KEY(id),
    CONSTRAINT fk_person
	FOREIGN KEY (person_id)
    REFERENCES person(id),
	CONSTRAINT fk_company
	FOREIGN KEY (company_id)
    REFERENCES company(id)
);

CREATE TABLE IF NOT EXISTS job_published(
	id INT GENERATED ALWAYS AS IDENTITY,
	description VARCHAR(200) NOT NULL,
	date_publish TIMESTAMP NOT NULL,
	job_id INT UNIQUE NOT NULL,
	PRIMARY KEY(id),
    CONSTRAINT fk_job
	FOREIGN KEY (job_id)
    REFERENCES job(id)
);

CREATE TABLE IF NOT EXISTS candidacy(
	id INT GENERATED ALWAYS AS IDENTITY,
	date_candidacy TIMESTAMP NOT NULL,
	status VARCHAR(20) NOT NULL,
	person_id INT NOT NULL,
	job_published_id INT UNIQUE NOT NULL,
	PRIMARY KEY(id),
    CONSTRAINT fk_person
	FOREIGN KEY (person_id)
    REFERENCES person(id),
	CONSTRAINT fk_job_published
	FOREIGN KEY (job_published_id)
    REFERENCES job_published(id)
);