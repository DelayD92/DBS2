-- <SEEDING DATABASE> ------------------------------------------------------------------------------------------------------------------------------------------
-- Genre
INSERT INTO Genre (Genre)
VALUES('Action');
INSERT INTO Genre (Genre)
VALUES('Crime');
INSERT INTO Genre (Genre)
VALUES('Adventure');
INSERT INTO Genre (Genre)
VALUES('Drama');
INSERT INTO Genre (Genre)
VALUES('Thriller');
INSERT INTO Genre (Genre)
VALUES('Biography');
INSERT INTO Genre (Genre)
VALUES('Documentary');
INSERT INTO Genre (Genre)
VALUES('History');
INSERT INTO Genre (Genre)
VALUES('Animation');

-- Movie
INSERT INTO Movie (Title, Year, Type)
VALUES('The Dark Knight', 2008, 'C');
INSERT INTO Movie (Title, Year, Type)
VALUES('Inception', 2010, 'C');
INSERT INTO Movie (Title, Year, Type)
VALUES('The Lord of the Rings: The Fellowship of the Ring', 2001, 'C');
INSERT INTO Movie (Title, Year, Type)
VALUES('The Lord of the Rings: The Return of the King', 2003, 'C');
INSERT INTO Movie (Title, Year, Type)
VALUES('The Dark Knight Rises', 2012, 'C');
INSERT INTO Movie (Title, Year, Type)
VALUES('The Lord of the Rings: The Two Towers', 2002, 'C');
INSERT INTO Movie (Title, Year, Type)
VALUES('Grand Theft Auto V', 2013, 'G');
INSERT INTO Movie (Title, Year, Type)
VALUES('The Last of Us', 2013, 'G');
INSERT INTO Movie (Title, Year, Type)
VALUES('The Normal Heart', 2014, 'T');
INSERT INTO Movie (Title, Year, Type)
VALUES('24', 2008, 'T');
INSERT INTO Movie (Title, Year, Type)
VALUES('You Don''t Know Jack', 2010, 'T');
INSERT INTO Movie (Title, Year, Type)
VALUES('The Sunset Limited', 2011, 'T');
INSERT INTO Movie (Title, Year, Type)
VALUES('Zeitgeist', 2007, 'V');
INSERT INTO Movie (Title, Year, Type)
VALUES('Batman: Under the Red Hood', 2010, 'V');


-- MovieGenre
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (1, 1);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (1, 2);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (2, 1);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (2, 3);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (3, 3);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (3, 4);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (4, 3);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (4, 4);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (5, 1);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (5, 5);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (6, 3);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (6, 4);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (7, 1);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (7, 3);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (8, 1);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (8, 3);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (9, 6);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (9, 4);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (10, 1);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (10, 3);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (11, 6);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (11, 4);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (12, 4);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (13, 7);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (13, 8);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (14, 1);
INSERT INTO MovieGenre (MovieId, GenreId) VALUES (14, 9);


-- Person
INSERT INTO Person (Name, Sex)
VALUES('Bale, Christian', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Ledger, Heath', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Eckhart, Aaron', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Caine, Michael', 'M');
INSERT INTO Person (Name, Sex)
VALUES('DiCaprio, Leonardo', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Gordon-Levitt, Joseph', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Page, Ellen', 'W');
INSERT INTO Person (Name, Sex)
VALUES('Hardy, Tom', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Howard, Alan', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Appleby, Noel', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Astin, Sean', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Baker, Sala', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Astin, Ali', 'W');
INSERT INTO Person (Name, Sex)
VALUES('Aston, David', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Oldman, Gary', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Allpress, Bruce', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Bach, John', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Fonteno, Shawn', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Luke, Ned', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Ogg, Steven', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Johnson, Gerald ''Slink''', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Baker, Troy', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Johnson, Ashley', 'W');
INSERT INTO Person (Name, Sex)
VALUES('Hayes, Hana', 'W');
INSERT INTO Person (Name, Sex)
VALUES('Pierce, Jeffrey', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Ruffalo, Mark', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Groff, Jonathan', 'M');
INSERT INTO Person (Name, Sex)
VALUES('De Julio, Frank', 'M');
INSERT INTO Person (Name, Sex)
VALUES('DeMeritt, William', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Sutherland, Kiefer', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Jones, Cherry', 'W');
INSERT INTO Person (Name, Sex)
VALUES('Gunton, Bob', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Feore, Colm', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Pacino, Al', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Vaccaro, Brenda', 'W');
INSERT INTO Person (Name, Sex)
VALUES('Goodman, John', 'M');
INSERT INTO Person (Name, Sex)
VALUES('O''Connell, Deirdre', 'W');
INSERT INTO Person (Name, Sex)
VALUES('Jackson, Samuel L.', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Jones, Tommy Lee', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Maxwell, Jordan', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Carlin, George', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Hicks, Bill', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Griffin, David Ray', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Greenwood, Bruce', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Ackles, Jensen', 'M');
INSERT INTO Person (Name, Sex)
VALUES('DiMaggio, John', 'M');
INSERT INTO Person (Name, Sex)
VALUES('Harris, Neil Patrick', 'M');


-- MovieCharacter
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(1, 1, 'Bruce Wayne', null, 1);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(1, 2, 'Joker', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(1, 3, 'Harvey Dent', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(1, 4, 'Alfred', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(2, 5, 'Cobb', null, 1);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(2, 6, 'Arthur', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(2, 7, 'Ariadne', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(2, 8, 'Eames', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(3, 9, 'Voice of the Ring', null, 1);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(3, 10, 'Everard Proudfoot', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(3, 11, 'Sam', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(3, 12, 'Sauron', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(4, 10, 'Everard Proudfoot', null, 1);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(4, 13, 'Elanor Gamgee', 'Alexandra Astin', 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(4, 11, 'Sam', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(4, 14, 'Gondorian Soldier 3', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(5, 1, 'Bruce Wayne', null, 1);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(5, 15, 'Commissioner Gordon', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(5, 8, 'Bane', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(5, 6, 'Blake', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(6, 16, 'Aldor', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(6, 11, 'Sam', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(6, 17, 'Madril', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(6, 12, 'Man Flesh Uruk', null, 5);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(7, 18, 'Franklin Clinton', null, 1);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(7, 19, 'Michael Townley/De Santa', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(7, 20, 'Trevor Philips', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(7, 21, 'Lamar Davis', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(8, 22, 'Joel', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(8, 23, 'Ellie', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(8, 24, 'Sarah', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(8, 39, 'Tommy', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(9, 25, 'Ned Weeks', null, 1);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(9, 27, 'Craig', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(9, 28, 'Nick', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(9, 29, 'Nino', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(10, 30, 'Jack Bauer', null, 1);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(10, 31, 'President-Elect Allison Taylor', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(10, 32, 'Ethan Kanin', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(10, 33, 'Henry Taylor', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(11, 34, 'Jack Kevorkian', null, 1);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(11, 35, 'Margo Janus', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(11, 36, 'Neal Nicol', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(11, 37, 'Linda', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(12, 38, 'Black', null, 1);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(12, 39, 'White', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(13, 40, 'Himself', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(13, 41, 'Himself', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(13, 42, 'Himself', null, 4);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(13, 43, 'Himself', null, 5);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(14, 44, 'Batman/Bruce Wayne', null, 1);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(14, 45, 'Jason Todd/Red Hood', null, 2);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(14, 46, 'Joker', null, 3);
INSERT INTO MovieCharacter (Movie, Person, Character, Alias, Position)
VALUES(14, 47, 'Dick Grayson/Nightwing', null, 4);

-- </SEEDING DATABASE> -----------------------------------------------------------------------------------------------------------------------------------------

COMMIT;
