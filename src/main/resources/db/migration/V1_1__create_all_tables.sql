CREATE TABLE  if not exists  Person
(
    id          BIGSERIAL       NOT NULL,
    firstnameCol VARCHAR(255) NOT NULL,
    lastnameCol VARCHAR(255) NOT NULL,
    CONSTRAINT pk_person PRIMARY KEY (id)
);