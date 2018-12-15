-- <SEEDING DATABASE> ------------------------------------------------------------------------------------------------------------------------------------------
-- Genre
INSERT INTO Genre (Id, Genre) VALUES (1, 'Action');
INSERT INTO Genre (Id, Genre) VALUES (2, 'Crime');
INSERT INTO Genre (Id, Genre) VALUES (3, 'Adventure');
INSERT INTO Genre (Id, Genre) VALUES (4, 'Drama');
INSERT INTO Genre (Id, Genre) VALUES (5, 'Thriller');
INSERT INTO Genre (Id, Genre) VALUES (6, 'Biography');
INSERT INTO Genre (Id, Genre) VALUES (7, 'Documentary');
INSERT INTO Genre (Id, Genre) VALUES (8, 'History');
INSERT INTO Genre (Id, Genre) VALUES (9, 'Animation');

-- Movie
INSERT INTO Movie (Id, Title, Year, Type) VALUES (1, 'The Dark Knight', 2008, 'C');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (2, 'Inception', 2010, 'C');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (3, 'The Lord of the Rings: The Fellowship of the Ring', 2001, 'C');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (4, 'The Lord of the Rings: The Return of the King', 2003, 'C');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (5, 'The Dark Knight Rises', 2012, 'C');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (6, 'The Lord of the Rings: The Two Towers', 2002, 'C');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (7, 'Grand Theft Auto V', 2013, 'G');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (8, 'The Last of Us', 2013, 'G');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (9, 'The Normal Heart', 2014, 'T');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (10, '24', 2008, 'T');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (11, 'You Don''t Know Jack', 2010, 'T');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (12, 'The Sunset Limited', 2011, 'T');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (13, 'Zeitgeist', 2007, 'V');
INSERT INTO Movie (Id, Title, Year, Type) VALUES (14, 'Batman: Under the Red Hood', 2010, 'V');


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
INSERT INTO Person (Id, Name, Sex) VALUES (1, 'Bale, Christian', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (2, 'Ledger, Heath', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (3, 'Eckhart, Aaron', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (4, 'Caine, Michael', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (5, 'DiCaprio, Leonardo', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (6, 'Gordon-Levitt, Joseph', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (7, 'Page, Ellen', 'W');
INSERT INTO Person (Id, Name, Sex) VALUES (8, 'Hardy, Tom', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (9, 'Howard, Alan', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (10, 'Appleby, Noel', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (11, 'Astin, Sean', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (12, 'Baker, Sala', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (13, 'Astin, Ali', 'W');
INSERT INTO Person (Id, Name, Sex) VALUES (14, 'Aston, David', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (15, 'Oldman, Gary', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (16, 'Allpress, Bruce', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (17, 'Bach, John', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (18, 'Fonteno, Shawn', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (19, 'Luke, Ned', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (20, 'Ogg, Steven', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (21, 'Johnson, Gerald ''Slink''', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (22, 'Baker, Troy', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (23, 'Johnson, Ashley', 'W');
INSERT INTO Person (Id, Name, Sex) VALUES (24, 'Hayes, Hana', 'W');
INSERT INTO Person (Id, Name, Sex) VALUES (25, 'Pierce, Jeffrey', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (26, 'Ruffalo, Mark', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (27, 'Groff, Jonathan', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (28, 'De Julio, Frank', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (29, 'DeMeritt, William', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (30, 'Sutherland, Kiefer', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (31, 'Jones, Cherry', 'W');
INSERT INTO Person (Id, Name, Sex) VALUES (32, 'Gunton, Bob', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (33, 'Feore, Colm', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (34, 'Pacino, Al', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (35, 'Vaccaro, Brenda', 'W');
INSERT INTO Person (Id, Name, Sex) VALUES (36, 'Goodman, John', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (37, 'O''Connell, Deirdre', 'W');
INSERT INTO Person (Id, Name, Sex) VALUES (38, 'Jackson, Samuel L.', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (39, 'Jones, Tommy Lee', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (40, 'Maxwell, Jordan', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (41, 'Carlin, George', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (42, 'Hicks, Bill', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (43, 'Griffin, David Ray', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (44, 'Greenwood, Bruce', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (45, 'Ackles, Jensen', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (46, 'DiMaggio, John', 'M');
INSERT INTO Person (Id, Name, Sex) VALUES (47, 'Harris, Neil Patrick', 'M');


-- MovieCharacter
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (1, 1, 1, 'Bruce Wayne', null, 1);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (2, 1, 2, 'Joker', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (3, 1, 3, 'Harvey Dent', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (4, 1, 4, 'Alfred', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (5, 2, 5, 'Cobb', null, 1);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (6, 2, 6, 'Arthur', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (7, 2, 7, 'Ariadne', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (8, 2, 8, 'Eames', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (9, 3, 9, 'Voice of the Ring', null, 1);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (10, 3, 10, 'Everard Proudfoot', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (11, 3, 11, 'Sam', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (12, 3, 12, 'Sauron', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (13, 4, 10, 'Everard Proudfoot', null, 1);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (14, 4, 13, 'Elanor Gamgee', 'Alexandra Astin', 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (15, 4, 11, 'Sam', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (16, 4, 14, 'Gondorian Soldier 3', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (17, 5, 1, 'Bruce Wayne', null, 1);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (18, 5, 15, 'Commissioner Gordon', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (19, 5, 8, 'Bane', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (20, 5, 6, 'Blake', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (21, 6, 16, 'Aldor', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (22, 6, 11, 'Sam', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (23, 6, 17, 'Madril', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (24, 6, 12, 'Man Flesh Uruk', null, 5);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (25, 7, 18, 'Franklin Clinton', null, 1);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (26, 7, 19, 'Michael Townley/De Santa', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (27, 7, 20, 'Trevor Philips', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (28, 7, 21, 'Lamar Davis', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (29, 8, 22, 'Joel', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (30, 8, 23, 'Ellie', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (31, 8, 24, 'Sarah', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (32, 8, 39, 'Tommy', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (33, 9, 25, 'Ned Weeks', null, 1);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (34, 9, 27, 'Craig', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (35, 9, 28, 'Nick', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (36, 9, 29, 'Nino', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (37, 10, 30, 'Jack Bauer', null, 1);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (38, 10, 31, 'President-Elect Allison Taylor', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (39, 10, 32, 'Ethan Kanin', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (40, 10, 33, 'Henry Taylor', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (41, 11, 34, 'Jack Kevorkian', null, 1);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (42, 11, 35, 'Margo Janus', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (43, 11, 36, 'Neal Nicol', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (44, 11, 37, 'Linda', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (45, 12, 38, 'Black', null, 1);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (46, 12, 39, 'White', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (47, 13, 40, 'Himself', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (48, 13, 41, 'Himself', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (49, 13, 42, 'Himself', null, 4);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (50, 13, 43, 'Himself', null, 5);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (51, 14, 44, 'Batman/Bruce Wayne', null, 1);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (52, 14, 45, 'Jason Todd/Red Hood', null, 2);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (53, 14, 46, 'Joker', null, 3);
INSERT INTO MovieCharacter (Id, Movie, Person, Character, Alias, Position) VALUES (54, 14, 47, 'Dick Grayson/Nightwing', null, 4);

-- </SEEDING DATABASE> -----------------------------------------------------------------------------------------------------------------------------------------

COMMIT;
