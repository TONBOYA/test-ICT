DROP TABLE IF EXISTS scene;

CREATE TABLE scene (
	unfolding_id integer NOT NULL,
	file_name text,
	ordinal integer,
	FOREIGN KEY (unfolding_id)
		REFERENCES unfolding(unfolding_id),
	PRIMARY KEY(unfolding_id,file_name)
);