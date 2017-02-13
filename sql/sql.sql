
DROP TABLE IF EXISTS personal;
DROP TABLE IF EXISTS practice_keyword;
DROP TABLE IF EXISTS scene;
DROP TABLE IF EXISTS unfolding;
DROP TABLE IF EXISTS practice;

DROP TABLE IF EXISTS practice_hierarchy_path;
DROP TABLE IF EXISTS content_hierarchy_path;
DROP TABLE IF EXISTS practice_hierarchy;
DROP TABLE IF EXISTS content_hierarchy;
DROP TABLE IF EXISTS account;


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

CREATE TABLE practice_hierarchy (
	practice_hierarchy_id serial  PRIMARY KEY,
	name text NOT NULL
);

CREATE TABLE practice_hierarchy_path (
	ancestor integer,
	descendant integer,
	FOREIGN KEY (ancestor)
		REFERENCES practice_hierarchy(practice_hierarchy_id),
	FOREIGN KEY (descendant)
		REFERENCES practice_hierarchy(practice_hierarchy_id),
	PRIMARY KEY (ancestor,descendant)
);

CREATE TABLE content_hierarchy (
	content_hierarchy_id serial  PRIMARY KEY,
	name text NOT NULL
);

CREATE TABLE content_hierarchy_path (
	ancestor integer,
	descendant integer,
	FOREIGN KEY (ancestor)
		REFERENCES content_hierarchy(content_hierarchy_id),
	FOREIGN KEY (descendant)
		REFERENCES content_hierarchy(content_hierarchy_id),
	PRIMARY KEY (ancestor,descendant)
);

CREATE TABLE practice (
	practice_id serial PRIMARY KEY,
	name text NOT NULL,
	summary text NOT NULL,
	aim text NOT NULL,
	reaction text NOT NULL,
	start_on date NOT NULL,
	practice_hierarchy_id integer NOT NULL,
	account_id integer NOT NULL,
	FOREIGN KEY (account_id)
		REFERENCES account(account_id)
);

CREATE TABLE practice_keyword (
	practice_id integer,
	keyword text NOT NULL,
	FOREIGN KEY (practice_id)
		REFERENCES practice(practice_id),
	PRIMARY KEY(practice_id,keyword)
);

CREATE TABLE unfolding (
	unfolding_id serial PRIMARY KEY,
	practice_id integer NOT NULL,
	ordinal integer NOT NULL,
	name text NOT NULL,
	required_minute integer NOT NULL,
	instruction text NOT NULL,
	special_instruction text NOT NULL,
	FOREIGN KEY (practice_id)
		REFERENCES practice(practice_id),
	UNIQUE(practice_id,ordinal)
);

CREATE TABLE scene (
	unfolding_id integer NOT NULL,
	path text NOT NULL,
	FOREIGN KEY (unfolding_id)
		REFERENCES unfolding(unfolding_id),
	PRIMARY KEY(unfolding_id,path)
);