-- <DROP TABLES AND SEQUENCES IF THEY EXIST> --------------------------------------------------------------------------------------------------------------------

DECLARE
  PROCEDURE dropObject(objName IN VARCHAR2, objType IN CHAR := 'T') IS
      TABLE_DOES_NOT_EXISTS EXCEPTION;
      SEQUENCE_DOES_NOT_EXISTS EXCEPTION;
    PRAGMA EXCEPTION_INIT (TABLE_DOES_NOT_EXISTS, -00942);
    PRAGMA EXCEPTION_INIT (SEQUENCE_DOES_NOT_EXISTS, -02289);
    BEGIN
      CASE objType
        WHEN 'T'
        THEN
          EXECUTE IMMEDIATE 'DROP TABLE ' || objName || ' CASCADE CONSTRAINTS';
        WHEN 'S'
        THEN
          EXECUTE IMMEDIATE 'DROP SEQUENCE ' || objName;
      END CASE;
      EXCEPTION
      WHEN TABLE_DOES_NOT_EXISTS THEN NULL;
      WHEN SEQUENCE_DOES_NOT_EXISTS THEN NULL;
      --WHEN OTHERS THEN NULL -- Don't use this, to see if there is any other issue while dropping the tables
    END;
BEGIN
  dropObject('MovieGenre');
  dropObject('PersonPlays');
  dropObject('Genre');
  dropObject('Movie');
  dropObject('MovieCharacter');
  dropObject('Person');
  dropObject('genre_sequence', 'S');
  dropObject('movie_sequence', 'S');
  dropObject('moviechar_sequence', 'S');
  dropObject('person_sequence', 'S');
END;
/

-- </DROP TABLES AND SEQUENCES IF THEY EXIST> ------------------------------------------------------------------------------------------------------------------

-- <MAIN TABLES> -----------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Genre (
  Id NUMBER(10)    NOT NULL,
  Genre   VARCHAR2(255) NOT NULL,
  CONSTRAINT pk_genre_id PRIMARY KEY (Id),
  CONSTRAINT uk_genre UNIQUE (Genre)
);

CREATE TABLE Movie (
  Id NUMBER(10)    NOT NULL,
  Title   VARCHAR2(255) NOT NULL,
  Year    NUMBER(4)     NOT NULL,
  Type    CHAR          NOT NULL,
  CONSTRAINT pk_movie_id PRIMARY KEY (Id)
);

CREATE TABLE MovieCharacter (
  Id NUMBER(10)    NOT NULL,
  MovieId     NUMBER(10)    NOT NULL,
  Character VARCHAR2(255) NOT NULL,
  Alias     VARCHAR2(255),
  Position  NUMBER(3),
  CONSTRAINT pk_moviechar_id PRIMARY KEY (Id),
  CONSTRAINT pk_moviechar_movie FOREIGN KEY (MovieId) REFERENCES Movie (Id)
);

CREATE TABLE Person (
  Id NUMBER(10)    NOT NULL,
  Name     VARCHAR2(255) NOT NULL,
  Sex      CHAR          NOT NULL,
  CONSTRAINT pk_person_id PRIMARY KEY (Id),
  CONSTRAINT uk_person_name UNIQUE (Name),
  CONSTRAINT ck_person_sex CHECK (Sex IN ('M', 'W'))
);

-- </MAIN TABLES> ----------------------------------------------------------------------------------------------------------------------------------------------

-- <RELATIONS> -------------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE MovieGenre (
  MovieId NUMBER(10) NOT NULL,
  GenreId NUMBER(10) NOT NULL,
  CONSTRAINT fk_movie_genre_movie FOREIGN KEY (MovieId) REFERENCES Movie (ID),
  CONSTRAINT fk_movie_genre_genre FOREIGN KEY (GenreId) REFERENCES Genre (ID)
);

CREATE TABLE PersonPlays (
  PersonId    NUMBER(10) NOT NULL,
  MovieCharId NUMBER(10) NOT NULL,
  CONSTRAINT fk_person_plays_person FOREIGN KEY (PersonId) REFERENCES Person (ID),
  CONSTRAINT fk_person_plays_movie_char FOREIGN KEY (MovieCharId) REFERENCES MovieCharacter (ID)
);

CREATE SEQUENCE moviechar_sequence;
CREATE OR REPLACE TRIGGER moviechar_on_insert
  BEFORE INSERT
  ON MovieCharacter
  FOR EACH ROW
  BEGIN
    SELECT moviechar_sequence.nextval INTO :new.Id FROM dual;
  END;

CREATE SEQUENCE person_sequence;
CREATE OR REPLACE TRIGGER person_on_insert
  BEFORE INSERT
  ON Person
  FOR EACH ROW
  BEGIN
    SELECT person_sequence.nextval INTO :new.Id FROM dual;
  END;

-- </RELATIONS> ------------------------------------------------------------------------------------------------------------------------------------------------

-- <TRIGGERS> --------------------------------------------------------------------------------------------------------------------------------------------------

CREATE SEQUENCE genre_sequence;
CREATE OR REPLACE TRIGGER genre_on_insert
  BEFORE INSERT
  ON Genre
  FOR EACH ROW
  BEGIN
    SELECT genre_sequence.nextval INTO :new.Id FROM dual;
  END;

CREATE SEQUENCE movie_sequence;
CREATE OR REPLACE TRIGGER movie_on_insert
  BEFORE INSERT
  ON Movie
  FOR EACH ROW
  BEGIN
    SELECT movie_sequence.nextval INTO :new.Id FROM dual;
  END;

-- </TRIGGERS> -------------------------------------------------------------------------------------------------------------------------------------------------

-- <SEEDING DATABASE> ------------------------------------------------------------------------------------------------------------------------------------------

-- Genre
INSERT INTO Genre (Id, Genre)
VALUES (1, 'Action');
INSERT INTO Genre (Id, Genre)
VALUES (2, 'Crime');
INSERT INTO Genre (Id, Genre)
VALUES (3, 'Adventure');
INSERT INTO Genre (Id, Genre)
VALUES (4, 'Drama');
INSERT INTO Genre (Id, Genre)
VALUES (5, 'Thriller');
INSERT INTO Genre (Id, Genre)
VALUES (6, 'Biography');
INSERT INTO Genre (Id, Genre)
VALUES (7, 'Documentary');
INSERT INTO Genre (Id, Genre)
VALUES (8, 'History');
INSERT INTO Genre (Id, Genre)
VALUES (9, 'Animation');

-- Movie
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (1, 'The Dark Knight', 2008, 'C');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (2, 'Inception', 2010, 'C');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (3, 'The Lord of the Rings: The Fellowship of the Ring', 2001, 'C');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (4, 'The Lord of the Rings: The Return of the King', 2003, 'C');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (5, 'The Dark Knight Rises', 2012, 'C');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (6, 'The Lord of the Rings: The Two Towers', 2002, 'C');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (7, 'Grand Theft Auto V', 2013, 'G');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (8, 'The Last of Us', 2013, 'G');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (9, 'The Normal Heart', 2014, 'T');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (10, '24', 2008, 'T');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (11, 'You Don''t Know Jack', 2010, 'T');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (12, 'The Sunset Limited', 2011, 'T');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (13, 'Zeitgeist', 2007, 'V');
INSERT INTO Movie (Id, Title, Year, Type)
VALUES (14, 'Batman: Under the Red Hood', 2010, 'V');

-- MovieCharacter
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (1, 1, 'Bruce Wayne', null, 1);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (2, 1, 'Joker', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (3, 1, 'Harvey Dent', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (4, 1, 'Alfred', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (5, 2, 'Cobb', null, 1);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (6, 2, 'Arthur', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (7, 2, 'Ariadne', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (8, 2, 'Eames', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (9, 3, 'Voice of the Ring', null, 1);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (10, 3, 'Everard Proudfoot', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (11, 3, 'Sam', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (12, 3, 'Sauron', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (13, 4, 'Everard Proudfoot', null, 1);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (14, 4, 'Elanor Gamgee', 'Alexandra Astin', 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (15, 4, 'Sam', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (16, 4, 'Gondorian Soldier 3', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (17, 5, 'Bruce Wayne', null, 1);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (18, 5, 'Commissioner Gordon', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (19, 5, 'Bane', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (20, 5, 'Blake', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (21, 6, 'Aldor', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (22, 6, 'Sam', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (23, 6, 'Madril', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (24, 6, 'Man Flesh Uruk', null, 5);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (25, 7, 'Franklin Clinton', null, 1);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (26, 7, 'Michael Townley/De Santa', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (27, 7, 'Trevor Philips', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (28, 7, 'Lamar Davis', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (29, 8, 'Joel', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (30, 8, 'Ellie', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (31, 8, 'Sarah', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (32, 8, 'Tommy', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (33, 9, 'Ned Weeks', null, 1);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (34, 9, 'Craig', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (35, 9, 'Nick', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (36, 9, 'Nino', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (37, 10, 'Jack Bauer', null, 1);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (38, 10, 'President-Elect Allison Taylor', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (39, 10, 'Ethan Kanin', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (40, 10, 'Henry Taylor', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (41, 11, 'Jack Kevorkian', null, 1);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (42, 11, 'Margo Janus', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (43, 11, 'Neal Nicol', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (44, 11, 'Linda', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (45, 12, 'Black', null, 1);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (46, 12, 'White', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (47, 13, 'Himself', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (48, 13, 'Himself', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (49, 13, 'Himself', null, 4);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (50, 13, 'Himself', null, 5);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (51, 14, 'Batman/Bruce Wayne', null, 1);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (52, 14, 'Jason Todd/Red Hood', null, 2);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (53, 14, 'Joker', null, 3);
INSERT INTO MovieCharacter (Id, MovieId, Character, Alias, Position)
VALUES (54, 14, 'Dick Grayson/Nightwing', null, 4);

-- MovieGenre
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (1, 1);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (1, 2);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (2, 1);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (2, 3);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (3, 3);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (3, 4);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (4, 3);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (4, 4);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (5, 1);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (5, 5);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (6, 3);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (6, 4);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (7, 1);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (7, 3);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (8, 1);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (8, 3);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (9, 6);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (9, 4);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (10, 1);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (10, 3);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (11, 6);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (11, 4);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (12, 4);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (13, 7);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (13, 8);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (14, 1);
INSERT INTO MovieGenre (MovieId, GenreId)
VALUES (14, 9);

-- Person
INSERT INTO Person (Id, Name, Sex)
VALUES (1, 'Bale, Christian', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (2, 'Ledger, Heath', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (3, 'Eckhart, Aaron', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (4, 'Caine, Michael', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (5, 'DiCaprio, Leonardo', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (6, 'Gordon-Levitt, Joseph', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (7, 'Page, Ellen', 'W');
INSERT INTO Person (Id, Name, Sex)
VALUES (8, 'Hardy, Tom', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (9, 'Howard, Alan', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (10, 'Appleby, Noel', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (11, 'Astin, Sean', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (12, 'Baker, Sala', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (13, 'Astin, Ali', 'W');
INSERT INTO Person (Id, Name, Sex)
VALUES (14, 'Aston, David', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (15, 'Oldman, Gary', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (16, 'Allpress, Bruce', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (17, 'Bach, John', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (18, 'Fonteno, Shawn', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (19, 'Luke, Ned', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (20, 'Ogg, Steven', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (21, 'Johnson, Gerald ''Slink''', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (22, 'Baker, Troy', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (23, 'Johnson, Ashley', 'W');
INSERT INTO Person (Id, Name, Sex)
VALUES (24, 'Hayes, Hana', 'W');
INSERT INTO Person (Id, Name, Sex)
VALUES (25, 'Pierce, Jeffrey', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (26, 'Ruffalo, Mark', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (27, 'Groff, Jonathan', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (28, 'De Julio, Frank', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (29, 'DeMeritt, William', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (30, 'Sutherland, Kiefer', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (31, 'Jones, Cherry', 'W');
INSERT INTO Person (Id, Name, Sex)
VALUES (32, 'Gunton, Bob', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (33, 'Feore, Colm', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (34, 'Pacino, Al', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (35, 'Vaccaro, Brenda', 'W');
INSERT INTO Person (Id, Name, Sex)
VALUES (36, 'Goodman, John', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (37, 'O''Connell, Deirdre', 'W');
INSERT INTO Person (Id, Name, Sex)
VALUES (38, 'Jackson, Samuel L.', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (39, 'Jones, Tommy Lee', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (40, 'Maxwell, Jordan', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (41, 'Carlin, George', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (42, 'Hicks, Bill', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (43, 'Griffin, David Ray', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (44, 'Greenwood, Bruce', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (45, 'Ackles, Jensen', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (46, 'DiMaggio, John', 'M');
INSERT INTO Person (Id, Name, Sex)
VALUES (47, 'Harris, Neil Patrick', 'M');
INSERT INTO PERSON (NAME, SEX)
VALUES ('TestFactory', 'M');

-- PersonPlays
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (1, 1);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (2, 2);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (3, 3);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (4, 4);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (5, 5);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (6, 6);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (7, 7);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (8, 8);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (9, 9);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (10, 10);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (11, 11);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (12, 12);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (10, 13);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (13, 14);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (11, 15);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (14, 16);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (1, 17);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (15, 18);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (8, 19);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (6, 20);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (16, 21);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (11, 22);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (17, 23);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (12, 24);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (18, 25);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (19, 26);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (20, 27);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (21, 28);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (22, 29);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (23, 30);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (24, 31);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (25, 32);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (26, 33);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (27, 34);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (28, 35);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (29, 36);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (30, 37);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (31, 38);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (32, 39);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (33, 40);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (34, 41);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (35, 42);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (36, 43);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (37, 44);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (38, 45);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (39, 46);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (40, 47);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (41, 48);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (42, 49);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (43, 50);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (44, 51);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (45, 52);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (46, 53);
INSERT INTO PersonPlays(PersonId, MovieCharId)
VALUES (47, 54);

-- </SEEDING DATABASE> -----------------------------------------------------------------------------------------------------------------------------------------

COMMIT;
