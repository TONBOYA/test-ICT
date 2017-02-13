DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS personal;

CREATE TABLE account (
	account_id serial PRIMARY KEY,
	login_name text NOT NULL UNIQUE,
	passphrase text NOT NULL
);

CREATE TABLE personal (
	account_id integer PRIMARY KEY,
	name text NOT NULL,
	belong_school text,
	email_address text,
	FOREIGN KEY (account_id) 
		REFERENCES account(account_id)
);


INSERT INTO account (login_name,passphrase)
	VALUES ('test','test');

INSERT INTO personal (account_id,name,belong_school,email_address)
	VALUES (1,'山田太郎','千歳科学技術大学','aa@gmail.com');