package de.hsh.dbs2.imdb.gui;

import de.hsh.dbs2.imdb.logic.dto.CharacterDTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Tabellenmodell, welches die Daten fuer eine Tabelle mit allen Characteren
 * eines Movies bereitstellt und verwaltet
 */
public class TabModelCharacter extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private List<CharacterDTO> data;
	private List<String> columns = new ArrayList<String>();

	public TabModelCharacter(List<CharacterDTO> data) {
		this.data = data;
		columns.add("Character");
		columns.add("Alias");
		columns.add("Player");
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column == 0)
			return data.get(row).getCharacter();
		else if (column == 1)
			return data.get(row).getAlias();
		else if (column == 2)
			return data.get(row).getPlayer();
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return columns.get(column);
	}
	
	public List<CharacterDTO> getData() {
		return data;
	}

	public void up(int index) {
		if (index > 0 && index < data.size()) {
			CharacterDTO element = data.remove(index);
			data.add(index-1, element);
		}
	}

	public void down(int index) {
		if (index >= 0 && index < data.size()-1) {
			CharacterDTO element = data.remove(index);
			data.add(index+1, element);
		}
	}

	public CharacterDTO getRow(int row) {
		return data.get(row);
	}

	public void addRow(CharacterDTO row) {
		data.add(row);		
	}

	public void deleteRow(int row) {
		data.remove(row);
	}
}
