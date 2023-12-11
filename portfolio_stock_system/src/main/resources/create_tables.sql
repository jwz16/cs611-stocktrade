-- Active: 1702191889234@@127.0.0.1@3306

CREATE TABLE IF NOT EXISTS
    customers (
        id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        first_name VARCHAR(64) NOT NULL,
        last_name VARCHAR(64) NOT NULL,
        username VARCHAR(32) NOT NULL UNIQUE,
        email VARCHAR(320) NOT NULL UNIQUE,
        date_of_birth CHAR(10) NOT NULL,
        ssn INT NOT NULL UNIQUE,
        customer_status CHAR(16) NOT NULL,
        password_hash TEXT NOT NULL
    );

CREATE TABLE IF NOT EXISTS
    managers (
        id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        first_name VARCHAR(64) NOT NULL,
        last_name VARCHAR(64) NOT NULL,
        username VARCHAR(32) NOT NULL UNIQUE,
        email VARCHAR(320) NOT NULL UNIQUE,
        date_of_birth CHAR(10) NOT NULL,
        password_hash TEXT NOT NULL
    );

CREATE TABLE IF NOT EXISTS
	securities (
		id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
		symbol VARCHAR(32) NOT NULL UNIQUE,
		security_name VARCHAR(128) NOT NULL,
		security_type VARCHAR(32) CHECK(
			security_type IN ('STOCK', 'OPTION')
		) NOT NULL,
		price DOUBLE PRECISION NOT NULL,
		tradable BOOLEAN NOT NULL
	);

CREATE TABLE IF NOT EXISTS
    customer_personal_accounts (
        id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        customer_id INT NOT NULL,
        account_name VARCHAR(64) NOT NULL UNIQUE,
        account_number BIGINT NOT NULL UNIQUE,
        routing_number BIGINT NOT NULL,
        FOREIGN KEY(customer_id) REFERENCES customers(id)
    );

CREATE TABLE IF NOT EXISTS
    customer_trading_accounts (
        id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        customer_id INT NOT NULL,
        account_name VARCHAR(64) NOT NULL UNIQUE,
        account_number BIGINT NOT NULL UNIQUE,
        total_value NUMERIC NOT NULL,
        cash NUMERIC NOT NULL,
        investment_value NUMERIC NOT NULL,
        unrealized_gains NUMERIC NOT NULL,
        realized_gains NUMERIC NOT NULL,
        account_status CHAR(16) CHECK(
            account_status IN ('PENDING', 'ACTIVE', 'DENIED')
        ) NOT NULL,
        FOREIGN KEY(customer_id) REFERENCES customers(id)
    );

CREATE TABLE IF NOT EXISTS
    customer_messages (
        id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        customer_id INT NOT NULL,
        message TEXT NOT NULL,
		sent_at DATETIME NOT NULL,
        FOREIGN KEY(customer_id) REFERENCES customers(id)
    );

CREATE TABLE IF NOT EXISTS
	customer_trade_orders (
		id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
		security_id INT NOT NULL,
		customer_trading_account_id INT NOT NULL,
		trade_quantity BIGINT NOT NULL,
		trade_price DOUBLE PRECISION NOT NULL,
		trade_type CHAR(4) CHECK(
            trade_type IN ('BUY', 'SELL')
        ) NOT NULL,
		trade_status CHAR(16) CHECK(
            trade_status IN ('PENDING', 'OPEN', 'EXECUTED', 'CANCELED')
        ) NOT NULL,
		trade_start_at DATETIME NOT NULL,
		trade_finish_at DATETIME,
		FOREIGN KEY(security_id) REFERENCES securities(id),
		FOREIGN KEY(customer_trading_account_id) REFERENCES customer_trading_accounts(id)
	);

CREATE TABLE IF NOT EXISTS
	customer_owned_securities (
		id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
		security_id INT NOT NULL,
		customer_trading_account_id INT NOT NULL,
		quantity BIGINT NOT NULL,
		bought_price DOUBLE PRECISION NOT NULL,
		bought_at DATETIME NOT NULL,
		FOREIGN KEY(security_id) REFERENCES securities(id),
		FOREIGN KEY(customer_trading_account_id) REFERENCES customer_trading_accounts(id)
	)