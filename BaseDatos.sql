DROP DATABASE IF EXISTS tcs_customers;

CREATE DATABASE tcs_customers;

USE tcs_customers;

DROP TABLE IF EXISTS customers;

DROP TABLE IF EXISTS persons;

CREATE TABLE persons (
    person_id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(50),
    age INT,
    identification VARCHAR(100) UNIQUE,
    address VARCHAR(255),
    phone VARCHAR(50)
);

CREATE TABLE customers (
    customer_id VARCHAR(36) PRIMARY KEY,
    person_id VARCHAR(36) UNIQUE,
    password VARCHAR(255) NOT NULL,
    status CHAR(1),
    CONSTRAINT fk_persons FOREIGN KEY (person_id) REFERENCES persons(person_id) ON DELETE CASCADE
);

DROP DATABASE IF EXISTS tcs_accounts;

CREATE DATABASE tcs_accounts;

USE tcs_accounts;

DROP TABLE IF EXISTS transactions;

DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
    account_id VARCHAR(36) PRIMARY KEY, 
    customer_id VARCHAR(36) NOT NULL,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type VARCHAR(50) NOT NULL,
    initial_balance DECIMAL(18, 2) NOT NULL,
    status CHAR(1) NOT NULL
);

CREATE TABLE transactions (
    transaction_id VARCHAR(36) PRIMARY KEY, 
    account_id VARCHAR(36) NOT NULL,
    date TIMESTAMP NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    amount DECIMAL(18, 2) NOT NULL,
    balance DECIMAL(18, 2) NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES Accounts(account_id)
);