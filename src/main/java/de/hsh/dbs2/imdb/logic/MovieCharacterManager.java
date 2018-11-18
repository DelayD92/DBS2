package de.hsh.dbs2.imdb.logic;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.imdb.logic.dto.CharacterDTO;
import de.hsh.dbs2.ue3.iter3.factory.PersonPlaysFactory;
import de.hsh.dbs2.ue3.iter3.model.MovieCharacter;
import de.hsh.dbs2.ue3.iter3.model.Person;

import java.util.ArrayList;
import java.util.List;

public class MovieCharacterManager {

    public static List<CharacterDTO> findMovieCastByMovieId(Long movieId) throws Exception {
        Database db = Database.getInstance();

        try {
            db.startTransaction();

            List<MovieCharacter> movieCharResults = new ArrayList<>();
            List<Person> personResults = new ArrayList<>();

            PersonPlaysFactory.findAndSetAllMovieCharsAndPersonsByMovieId(
                movieId,
                movieCharResults,
                personResults
            );

            List<CharacterDTO> results = new ArrayList<>();
            for(int i = 0; i < movieCharResults.size(); i++) {
                CharacterDTO charDTO = new CharacterDTO();
                charDTO.setCharacter(movieCharResults.get(i).getCharacter());
                charDTO.setAlias(movieCharResults.get(i).getAlias());
                charDTO.setPlayer(personResults.get(i).getName());
                results.add(charDTO);
            }

            return results;
        } finally {
            db.commitTransaction();
        }
    }
}
