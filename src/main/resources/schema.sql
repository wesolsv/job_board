DROP SCHEMA job_board CASCADE;

CREATE SCHEMA IF NOT EXISTS job_board AUTHORIZATION postgres;

SET search_path TO job_board;

CREATE TABLE IF NOT EXISTS person(
	id INT GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(150) NOT NULL,
	phone VARCHAR(15) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	cpf  VARCHAR(11) UNIQUE NOT NULL,
	password  VARCHAR(100) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS company(
	id INT GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(150) NOT NULL,
	phone VARCHAR(15) UNIQUE NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	cnpj  VARCHAR(14) UNIQUE NOT NULL,
	password  VARCHAR(100) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS users(
	id INT GENERATED ALWAYS AS IDENTITY,
	email VARCHAR(100) UNIQUE NOT NULL,
	password  VARCHAR(100) NOT NULL,
	person_id INT NULL,
    company_id INT NULL,
      PRIMARY KEY(id),
      CONSTRAINT fk_person
      FOREIGN KEY (person_id)
      REFERENCES person(id),
      CONSTRAINT fk_company
      FOREIGN KEY (company_id)
      REFERENCES company(id)
);

CREATE TABLE IF NOT EXISTS roles (
        id INT GENERATED ALWAYS AS IDENTITY,
        name VARCHAR(15) NOT NULL,
        PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS users_roles(
    users_id INT,
    roles_id INT,
    CONSTRAINT fk_users
    FOREIGN KEY (users_id)
    REFERENCES users(id),
    CONSTRAINT fk_roles
    FOREIGN KEY (roles_id)
    REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS job(
	id INT GENERATED ALWAYS AS IDENTITY,
	opportunity VARCHAR(50) NOT NULL,
	description VARCHAR(200) NOT NULL,
	type VARCHAR(20) NOT NULL,
	salary DECIMAL NOT NULL,
	benefits  VARCHAR(50) NOT NULL,
	status VARCHAR(20) NOT NULL,
	person_id INT UNIQUE NULL,
	company_id INT NULL,
	date_publish TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
    CONSTRAINT fk_person
	FOREIGN KEY (person_id)
    REFERENCES person(id),
	CONSTRAINT fk_company
	FOREIGN KEY (company_id)
    REFERENCES company(id)
);

CREATE TABLE IF NOT EXISTS candidacy(
	id INT GENERATED ALWAYS AS IDENTITY,
	date_candidacy TIMESTAMP NOT NULL,
	status VARCHAR(20) NOT NULL,
	person_id INT NOT NULL,
	job_id INT NOT NULL,
	PRIMARY KEY(id),
    CONSTRAINT fk_person
	FOREIGN KEY (person_id)
    REFERENCES person(id),
	CONSTRAINT fk_job
	FOREIGN KEY (job_id)
    REFERENCES job(id)
);