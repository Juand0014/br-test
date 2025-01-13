CREATE SEQUENCE IF NOT EXISTS clients_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE clients
(
    id             BIGINT       NOT NULL,
    firstName      VARCHAR(255) NOT NULL,
    middleName     VARCHAR(255),
    firstLastName  VARCHAR(255) NOT NULL,
    secondLastName VARCHAR(255),
    email          VARCHAR(255) NOT NULL,
    address        VARCHAR(255) NOT NULL,
    phoneNumber    VARCHAR(10)  NOT NULL,
    countryCode    VARCHAR(2)   NOT NULL,
    demonym        VARCHAR(255),
    CONSTRAINT pk_clients PRIMARY KEY (id)
);