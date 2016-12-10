DROP TABLE pms.projects CASCADE;
DROP TABLE pms.companies CASCADE;
DROP TABLE pms.customers CASCADE;
DROP TABLE pms.developers CASCADE;
DROP TABLE pms.skills CASCADE;
DROP TABLE pms.developers_skills CASCADE;
DROP TABLE pms.projects_developers CASCADE;

CREATE TABLE pms.companies
(
    id serial NOT NULL,
    company_name character varying(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE pms.customers
(
    id serial NOT NULL,
    customer_name character varying(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE pms.projects
(
    id serial NOT NULL,
	  company_id integer NOT NULL,
    customer_id integer NOT NULL,
    project_name character varying(50) NOT NULL,
    PRIMARY KEY (id),
	FOREIGN KEY (company_id)
        REFERENCES pms.companies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	FOREIGN KEY (customer_id)
        REFERENCES pms.customers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE pms.developers
(
    id serial NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50),
    company_id integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (company_id)
        REFERENCES pms.companies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE pms.skills
(
    id serial NOT NULL,
    skill_name character varying(50) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE pms.developers_skills
(
    developer_id integer NOT NULL,
    skill_id integer NOT NULL,
    PRIMARY KEY (developer_id, skill_id),
    FOREIGN KEY (developer_id)
        REFERENCES pms.developers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (skill_id)
        REFERENCES pms.skills (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE pms.projects_developers
(
    project_id integer NOT NULL,
	  developer_id integer NOT NULL,
    PRIMARY KEY (project_id, developer_id),
	  FOREIGN KEY (project_id)
        REFERENCES pms.projects (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (developer_id)
        REFERENCES pms.developers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);