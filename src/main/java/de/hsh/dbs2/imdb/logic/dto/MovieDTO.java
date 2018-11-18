package de.hsh.dbs2.imdb.logic.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Data Transfer Object (DTO) fuer Objekte der Klasse Movie
 * Enthaelt alles noetige fuer die Kommunikation GUI <-> Geschaeftslogik.
 * Dazue gehoeren auch alle dem Movie zugeordneten Genres und Charaktere.
 * Enthaelt die ID; falls es sich um einen neuen Movie handelt, der noch nicht
 * in der Datenbank vorhanden ist, ist die ID null.
 * @author felix
 */
public class MovieDTO {

	private Long id = null;
	private String title = "";
	private String type = "C";
	private int year = 0;
	private Set<String> genres = new HashSet<String>();
	private List<CharacterDTO> characters = new ArrayList<CharacterDTO>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public void addGenre(String genre) {
		genres.add(genre);
	}
	public void addCharacter(CharacterDTO cdto) {
		characters.add(cdto);
	}
	public Set<String> getGenres() {
		return genres;
	}
	public List<CharacterDTO> getCharacters() {
		return characters;
	}
	public void setCharacters(List<CharacterDTO> characters) {
		this.characters = characters;
	}
	public void setGenres(Set<String> genres2) {
		this.genres = genres2;
	}

    @Override
    public String toString() {
        return String.format("Id: %d, Title: %s, Year: %d, Type: %s", id, title, year, type);
    }
}
