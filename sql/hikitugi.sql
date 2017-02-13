--DROP TABLE IF EXISTS scene;
--DROP TABLE IF EXISTS unfolding;
--DROP TABLE IF EXISTS practice_keyword;
--DROP TABLE IF EXISTS practice;
--DROP TABLE IF EXISTS content_hierarchy;
--DROP TABLE IF EXISTS practice_hierarchy;
--DROP TABLE IF EXISTS personal;
--DROP TABLE IF EXISTS account;


ALTER TABLE account ADD available boolean not null default true;

ALTER TABLE practice ADD read_times integer not null default 0, ADD type text not null default 'EDITED', ADD approval boolean not null default FALSE, ADD ordinal integer not null default 1;

CREATE TABLE practice_read (
	practice_id integer,
	account_id integer,
	read_start_at timestamp,
	FOREIGN KEY (practice_id)
		REFERENCES practice(practice_id),
	FOREIGN KEY (account_id)
		REFERENCES account(account_id),
	PRIMARY KEY(practice_id,account_id,read_start_at)
);

CREATE TABLE practice_good (
	practice_id integer,
	account_id integer,
	FOREIGN KEY (practice_id)
		REFERENCES practice(practice_id),
	FOREIGN KEY (account_id)
		REFERENCES account(account_id),
	PRIMARY KEY (practice_id,account_id)
);

ALTER TABLE scene ADD type text;

CREATE TABLE content (
	content_id serial PRIMARY KEY,
	file_name text not null,
	name text not null,
	input_at timestamp,
	content_hierarchy_id integer,
	account_id integer,
	how_to_use text,
	ordinal integer,
	type text,
	FOREIGN KEY (content_hierarchy_id)
		REFERENCES content_hierarchy(content_hierarchy_id),
	FOREIGN KEY (account_id)
		REFERENCES account(account_id),
	UNIQUE (content_hierarchy_id,ordinal),
	UNIQUE (content_hierarchy_id,name)
);

CREATE TABLE content_image (
	content_id integer,
	file_name text,
	ordinal integer,
	FOREIGN KEY (content_id)
		REFERENCES content(content_id),
	PRIMARY KEY (content_id,file_name)
);

CREATE TABLE content_keyword (
	content_id integer,
	keyword text,
	FOREIGN KEY (content_id)
		REFERENCES content(content_id),
	PRIMARY KEY (content_id,keyword)
);

CREATE TABLE content_practice (
	practice_id integer,
	content_id integer,
	FOREIGN KEY (practice_id)
		REFERENCES practice(practice_id),
	FOREIGN KEY (content_id)
		REFERENCES content(content_id),
	PRIMARY KEY (practice_id,content_id)
);

CREATE TABLE comment (
	practice_id integer,
	account_id integer,
	input_at timestamp,
	matter text,
	available boolean not null default TRUE,
	FOREIGN KEY (practice_id)
		REFERENCES practice(practice_id),
	FOREIGN KEY (account_id)
		REFERENCES account(account_id),
	PRIMARY KEY (practice_id,account_id,input_at)
	
);