package de.hsh.dbs2.imdb.gui;

import de.hsh.dbs2.imdb.logic.dto.MovieDTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Tabellenmodell fuer eine Tabelle mit allen Filmen
 */
public class TabModelMovie extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private List<MovieDTO> data;
	private List<String> columns = new ArrayList<String>();

	public TabModelMovie(List<MovieDTO> data) {
		this.data = data;
		columns.add("Id");
		columns.add("Title");
		columns.add("Type");
		columns.add("Year");
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
			return data.get(row).getId();
		else if (column == 1)
			return data.get(row).getTitle();
		else if (column == 2)
			return data.get(row).getType();
		else if (column == 3)
			return data.get(row).getYear();
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return columns.get(column);
	}
	
	public List<MovieDTO> getData() {
		return data;
	}

	public void up(int index) {
		if (index > 0 && index < data.size()) {
			MovieDTO element = data.remove(index);
			data.add(index-1, element);
		}
	}

	public void down(int index) {
		if (index >= 0 && index < data.size()-1) {
			MovieDTO element = data.remove(index);
			data.add(index+1, element);
		}
	}

	public MovieDTO getRow(int row) {
		return data.get(row);
	}

	public void addRow(MovieDTO row) {
		data.add(row);		
	}

	public void deleteRow(int row) {
		data.remove(row);
	}

	public void changeRow(int row, MovieDTO rowData) {
		data.set(row,  rowData);
	}

}
