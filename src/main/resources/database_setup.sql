-- Creates an integer sequence used as a primary key for movies (films).
CREATE SEQUENCE film_id AS
	INTEGER
	START WITH 1
	INCREMENT BY 1;

-- Creates the table where films are stored.
CREATE TABLE APP.film (
	film_id 		INTEGER NOT NULL,
	poster_link		VARCHAR(255),
	title			VARCHAR(255) NOT NULL,
	release_year	INTEGER NOT NULL,
	cert			VARCHAR(255),
	runtime			INTEGER NOT NULL,
	genres			VARCHAR(255) NOT NULL,
	imdb_rating		FLOAT,
	overview		VARCHAR(255) NOT NULL,
	metascore		INTEGER,
	director		VARCHAR(255) NOT NULL,
	star1			VARCHAR(255),
	star2			VARCHAR(255),
	star3			VARCHAR(255),
	star4			VARCHAR(255),
	votecount		INTEGER,
	gross_revenue	INTEGER NOT NULL,
	CONSTRAINT FILM_PK PRIMARY KEY (film_id)
);