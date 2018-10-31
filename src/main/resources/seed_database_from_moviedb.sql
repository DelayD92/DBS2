-- USE THIS SCRIPT ONLY AFTER YOU RUN "dbInit.sql"

-- CLEAN TABLES IF THEY EXIST -------------------------------------------------------------------------------------------
DECLARE
  PROCEDURE cleanTable(tableName IN VARCHAR2, idColName IN VARCHAR2 := '') IS
    BEGIN
      EXECUTE IMMEDIATE 'DELETE FROM ' || tableName;
      EXECUTE IMMEDIATE 'ALTER TABLE ' || tableName || ' MODIFY(' || idColName ||
                        ' Generated as Identity (START WITH 1))';
      EXCEPTION WHEN OTHERS
      THEN NULL;
    END;
BEGIN
  cleanTable('MovieGenre');
  cleanTable('MovieCast');
  cleanTable('PersonPlays');
  cleanTable('Genre', 'GenreID');
  cleanTable('Movie', 'MovieID');
  cleanTable('MovieCharacter', 'MovCharID');
  cleanTable('Person', 'PersonID');
END;
/

DECLARE
  dummyCounter          number := 0;
  movieIdCounter        number := 0;
  v_genre_id            NUMBER(10);
  v_genre               VARCHAR2(255);

  v_movCharNewInsertId  NUMBER(10);
  v_personInsertId      NUMBER(10);
  v_personPlaysInsertId NUMBER(10);

  type movie_types_arr is varray (4) of CHAR;
  movie_types           movie_types_arr := movie_types_arr('V', 'T', 'C', 'G');

  v_movieNewInsertId    NUMBER(10);
  v_movie_original_id NUMBER(10);
  v_title             VARCHAR2(255);
  v_year              NUMBER(4);
  v_type              CHAR;

  CURSOR movieCursor(movieType CHAR)
  IS
    SELECT DISTINCT M.ID v_movie_original_id, M.TITLE v_title, M.YEAR v_year, M.TYPE v_type
    FROM MOVIEDB.MOVIE M
           JOIN MOVIEDB.RATING R ON M.ID = R.MOVIE
    WHERE M.TYPE = movieType
      AND M.YEAR > 2000
      AND R.RATING > 7.0
    ORDER BY R.VOTES DESC, R.RATING DESC
    FETCH FIRST 4 ROWS ONLY;
BEGIN

  FOR i in 1 .. movie_types.COUNT LOOP

    FOR l_movieRecord IN movieCursor(movie_types(i)) LOOP
      movieIdCounter := movieIdCounter + 1;

      -- insert movie
      INSERT INTO Movie (MovieID, Title, Year, Type)
      VALUES (NULL, l_movieRecord.v_title, l_movieRecord.v_year, l_movieRecord.v_type)
          RETURNING MovieID into v_movieNewInsertId;

      -- insert genre and movie genre
      FOR genre_loop IN (SELECT * FROM MOVIEDB.GENRE G WHERE G.Movie = l_movieRecord.v_movie_original_id) LOOP
        BEGIN
          SELECT GenreID INTO v_genre_id FROM Genre WHERE Genre = genre_loop.Genre;
          EXCEPTION
          WHEN NO_DATA_FOUND
          THEN
            INSERT INTO GENRE (Genre) VALUES (genre_loop.Genre) RETURNING GenreID INTO v_genre_id;
        END;
        INSERT INTO MovieGenre (Movie, Genre) VALUES (v_movieNewInsertId, v_genre_id);
      END LOOP genre_loop;

      -- insert person, movie character and movie cast
      FOR movie_char_loop IN (SELECT *
                              FROM MOVIEDB.PLAYS PLA
                                     JOIN MOVIEDB.PERSON PER ON PER.ID = PLA.PLAYER
                              WHERE PLA.Movie = l_movieRecord.v_movie_original_id
                                  -- AND PER.NAME IS NOT NULL AND PER.SEX IS NOT NULL AND PLA.POS IS NOT NULL
                                AND PER.NAME IS NOT NULL
                              ORDER BY PLA.Pos
                              FETCH FIRST 4 ROWS ONLY) LOOP

        movie_char_loop.name := REGEXP_REPLACE(movie_char_loop.name, '\s?\(.+\)', '');

        BEGIN
          BEGIN
            SELECT PersonID INTO v_personInsertId FROM Person WHERE Name = movie_char_loop.Name;
            EXCEPTION
            WHEN NO_DATA_FOUND
            THEN
              INSERT INTO Person (Name, Sex)
              VALUES (movie_char_loop.Name, (CASE WHEN movie_char_loop.Sex = 'f' THEN 'W' ELSE 'M' END))
                  RETURNING PersonID INTO v_personInsertId;
          END;
          EXCEPTION WHEN OTHERS
          THEN NULL;
        END;

        INSERT INTO MovieCharacter (Character, Alias, Position)
        VALUES (movie_char_loop.Character, movie_char_loop.Alias, movie_char_loop.Pos)
            RETURNING MovCharID INTO v_movCharNewInsertId;

        INSERT INTO PersonPlays (Person, MovieChar) VALUES (v_personInsertId, v_movCharNewInsertId);

        INSERT INTO MovieCast (Movie, MovieChar) VALUES (v_movieNewInsertId, v_movCharNewInsertId);

      END LOOP movie_char_loop;

    END LOOP;
  END LOOP;

END;

COMMIT;
