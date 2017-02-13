DROP TABLE IF EXISTS scene;
DROP TABLE IF EXISTS unfolding;


CREATE TABLE unfolding (
	unfolding_id serial PRIMARY KEY,
	practice_id integer NOT NULL,
	ordinal integer NOT NULL,
	name text NOT NULL,
	required_minute integer NOT NULL,
	instruction text,
	special_instruction text,
	FOREIGN KEY (practice_id)
		REFERENCES practice(practice_id),
	UNIQUE(practice_id,ordinal)
);

CREATE TABLE scene (
	unfolding_id integer NOT NULL,
	file_name text,
	ordinal integer,
	FOREIGN KEY (unfolding_id)
		REFERENCES unfolding(unfolding_id),
	PRIMARY KEY(unfolding_id,file_name)
);
