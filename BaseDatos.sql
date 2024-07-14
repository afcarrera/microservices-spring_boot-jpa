DROP DATABASE IF EXISTS carrera_customers;

CREATE DATABASE carrera_customers;

USE carrera_customers;

DROP TABLE IF EXISTS carrera_customers.customers;

DROP TABLE IF EXISTS carrera_customers.persons;

CREATE TABLE carrera_customers.persons (
    person_id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender_type_code VARCHAR(5) NOT NULL,
    gender_value_code VARCHAR(5) NOT NULL,
    age TINYINT(4) UNSIGNED DEFAULT NULL,
    identification VARCHAR(100) UNIQUE,
    address VARCHAR(255),
    phone VARCHAR(50)
);

CREATE TABLE carrera_customers.customers (
    customer_id VARCHAR(36) UNIQUE,
    person_id VARCHAR(36) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    status CHAR(1),
    CONSTRAINT fk_persons FOREIGN KEY (person_id) REFERENCES carrera_customers.persons(person_id) ON DELETE CASCADE
);

DROP DATABASE IF EXISTS carrera_accounts;

CREATE DATABASE carrera_accounts;

USE carrera_accounts;

DROP TABLE IF EXISTS carrera_accounts.transactions;

DROP TABLE IF EXISTS carrera_accounts.accounts;

CREATE TABLE carrera_accounts.accounts (
    account_id VARCHAR(36) PRIMARY KEY, 
    customer_id VARCHAR(36) NOT NULL,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type_code VARCHAR(5) NOT NULL,
    account_value_code VARCHAR(5) NOT NULL,
    initial_balance DECIMAL(18, 2) NOT NULL,
    status CHAR(1) NOT NULL
);

CREATE TABLE carrera_accounts.transactions (
    transaction_id VARCHAR(36) PRIMARY KEY, 
    account_id VARCHAR(36) NOT NULL,
    date TIMESTAMP NOT NULL,
    transaction_type_code VARCHAR(5) NOT NULL,
    transaction_value_code VARCHAR(5) NOT NULL,
    amount DECIMAL(18, 2) NOT NULL,
    balance DECIMAL(18, 2) NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES carrera_accounts.accounts(account_id)
);

DROP DATABASE IF EXISTS carrera_catalogs;

CREATE DATABASE carrera_catalogs;

USE carrera_catalogs;

DROP TABLE IF EXISTS carrera_catalogs.catalog_types;

DROP TABLE IF EXISTS carrera_catalogs.catalog_values;

CREATE TABLE carrera_catalogs.catalog_types (
    type_id SERIAL PRIMARY KEY,
    code VARCHAR(5) NOT NULL UNIQUE, 
    value VARCHAR(50) NOT NULL,
    status CHAR(1) NOT NULL DEFAULT '1'
);

CREATE TABLE carrera_catalogs.catalog_values (
    value_id SERIAL PRIMARY KEY, 
    type_code VARCHAR(5) NOT NULL,
    code VARCHAR(5) NOT NULL, 
    value VARCHAR(50) NOT NULL,
    status CHAR(1) NOT NULL DEFAULT '1',
    CONSTRAINT fk_account FOREIGN KEY (type_code) REFERENCES carrera_catalogs.catalog_types(code),
    UNIQUE (type_code, code) 
);


INSERT INTO carrera_catalogs.catalog_types (`type_id`, `code`, `value`, `status`) VALUES
(1, 'ACCTP', 'Bank account types', '1'),
(2, 'TRX', 'Bank account transactions', '1'),
(3, 'GNDER', 'client gender', '1');

INSERT INTO carrera_catalogs.catalog_values (`value_id`, `type_code`, `code`, `value`, `status`) VALUES
(1, 'ACCTP', 'SAVES', 'Savings account', '1'),
(2, 'ACCTP', 'CHECK', 'Checking account', '1'),
(3, 'TRX', 'WITHD', 'Withdrawal transaction', '1'),
(4, 'TRX', 'DEPOS', 'Deposit transaction', '1'),
(5, 'GNDER', 'MALE', 'Male gender', '1'),
(6, 'GNDER', 'FEMA', 'Female gender', '1');
