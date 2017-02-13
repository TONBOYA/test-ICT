DROP TABLE IF EXISTS scene;
DROP TABLE IF EXISTS unfolding;
DROP TABLE IF EXISTS practice_keyword;
DROP TABLE IF EXISTS practice;
DROP TABLE IF EXISTS content_hierarchy;
DROP TABLE IF EXISTS practice_hierarchy;
DROP TABLE IF EXISTS personal;
DROP TABLE IF EXISTS account;


CREATE TABLE account (
	account_id serial PRIMARY KEY,
	login_name text NOT NULL UNIQUE,
	passphrase text NOT NULL,
	role text NOT NULL
);

CREATE TABLE personal (
	account_id integer PRIMARY KEY,
	name text NOT NULL UNIQUE,
	belong_school text,
	email_address text,
	FOREIGN KEY (account_id)
		REFERENCES account(account_id)
);

CREATE TABLE practice_hierarchy (
	practice_hierarchy_id serial PRIMARY KEY,
	name text NOT NULL,
	one_level_up integer,
	ordinal integer
);

CREATE TABLE content_hierarchy (
	content_hierarchy_id serial PRIMARY KEY,
	name text NOT NULL,
	one_level_up integer
);

CREATE TABLE practice (
	practice_id serial PRIMARY KEY,
	name text NOT NULL,
	summary text,
	aim text,
	challenge text,
	reaction text,
	start_on date,
	account_id integer NOT NULL,
	practice_hierarchy_id integer NOT NULL,
	FOREIGN KEY (account_id)
		REFERENCES account(account_id),
	FOREIGN KEY (practice_hierarchy_id)
		REFERENCES practice_hierarchy(practice_hierarchy_id)
);

CREATE TABLE practice_keyword (
	practice_id integer,
	keyword text NOT NULL,
	FOREIGN KEY (practice_id)
		REFERENCES practice(practice_id)
	PRIMARY KEY(practice_id,keyword)
);

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

INSERT INTO account (login_name,passphrase,role)
	VALUES ('test','test','ADMIN'),
	       ('test2','test','ADMIN'),
	       ('test3','test','USER'),
	       ('test4','test','USER');

INSERT INTO personal (account_id,name,belong_school,email_address)
	VALUES 	(1,'間所','千歳科学技術大学','aa@gmail.com'),
		(2,'高橋','千歳科学技術大学','aa@gmail.com'),
		(3,'加藤','千歳科学技術大学','aa@gmail.com'),
		(4,'久保','千歳科学技術大学','aa@gmail.com');

INSERT INTO practice_hierarchy (name,one_level_up,ordinal)
	VALUES  ('実践ルートディレクトリ',null),
		('理科',1,1),
		('中学3年',2,3),
		('生命の連続性',3,1),
		('生物の成長',4,1),
		('生物のふえ方と遺伝',4,2),
		('化学物質とイオン',3,2),
		('水溶液とイオン',7,1),
		('酸・アルカリと塩',7,2),
		('運動とエネルギー',3,3),
		('力のつり合いと合成・分解',10,1),
		('力と物体の運動',10,2),
		('仕事とエネルギー',10,3),
		('いろいろなエネルギーとその移り変わり',10,4),
		('エネルギー資源とその利用',10,5),
		('地球と宇宙',3,4),
		('地球の運動と天体の動き',16,1),
		('太陽系の天体',16,2),
		('恒星の世界',16,3),
		('自然と人間',3,4),
		('自然界のつり合い',20,1),
		('人間と環境',20,2),
		('自然と人間のかかわり',20,3),
		('科学技術と人間',20,4),
		('科学技術の利用と環境保全',20,5),
		('中学2年',2,2),
		('化学変化と原子・分子',26,1),
		('物質の成り立ち',27,1),
		('さまざまな化学変化',27,2),
		('化学変化と物質の質量の規則性',27,3),
		('動物のくらしやなかまと生物の変遷',26,2),
		('生命の体を作る細胞',31,1),
		('生命を維持するはたらき',31,2),
		('感覚と運動のしくみ',31,3),
		('動物のなかまと生物の進化',31,4),
		('地球の大気と天気の変化',26,3),
		('空気中の水の変化',36,1),
		('大気の動きと天気の変化',36,2),
		('大気の動きと日本の四季',36,3),
		('電流の性質とその利用',26,4),
		('電流の性質',40,1),
		('電流の正体',40,2),
		('電流と磁界',40,3),
		('中学1年',2,1),
		('植物のくらしとなかま',44,1),
		('花のつくりとはたらき',45,1),
		('根や茎のつくりとはたらき',45,2),
		('葉のつくりとはたらき',45,3),
		('植物のなかま分け',45,4),
		('みのまわりの物質',44,2),
		('いろいろな物質とその性質',50,1),
		('いろいろな気体とその性質',50,2),
		('水溶液の性質',50,3),
		('物質の状態とその変化',50,4),
		('光・音・力による現象',44,3),
		('光による現象',55,1),
		('音による現象',55,2),
		('力による現象',55,3),
		('活きている地球',44,4),
		('大地が火をふく',59,1),
		('大地がゆれる',59,2),
		('大地は語る',59,3),
		('数学',1,2),
		('国語',1,3),
		('英語',1,4),
		('社会',1,5);


