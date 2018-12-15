package de.hsh.dbs2.ue4.imdb.gui;

import de.hsh.dbs2.ue4.imdb.logic.PersonManager;

import javax.swing.*;
import java.util.List;

public class CharacterDialogCallback {

	private PersonManager pm = new PersonManager();

	public List<String> getPersonList(String text) {
		try {
			List<String> persons = pm.getPersonList(text);
			return persons;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Fehler bei der Personensuche: \n" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
