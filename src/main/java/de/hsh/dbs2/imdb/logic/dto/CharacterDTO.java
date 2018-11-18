package de.hsh.dbs2.imdb.logic.dto;

/**
 * Data Transfer Object (DTO) fuer Objekte der Klasse Character
 * Enthaelt alles noetige fuer die Kommunikation GUI <-> Geschaeftslogik.
 */
public class CharacterDTO {
	private String character;
	private String alias;
	private String player;
	
	public CharacterDTO(CharacterDTO that) {
		this.character = that.character;
		this.player = that.player;
		this.alias = that.alias;
	}
	
	public CharacterDTO() {		
	}
	
	public String getCharacter() {
		return character;
	}
	
	public void setCharacter(String character) {
		this.character = character;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public void setPlayer(String player) {
		this.player = player;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
