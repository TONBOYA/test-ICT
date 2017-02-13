DROP TABLE IF EXISTS practice_hierarchy_path;
DROP TABLE IF EXISTS content_hierarchy_path;


ALTER TABLE practice_hierarchy ADD one_level_up integer
	REFERENCES practice_hierarchy(practice_hierarchy_id);
ALTER TABLE content_hierarchy ADD one_level_up integer
	REFERENCES content_hierarchy(content_hierarchy_id);