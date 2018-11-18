package de.hsh.dbs2.imdb.gui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Tabellenmodell mit allen Daten fuer einen Tabelle aus Personen
 *
 */
public class TabModelPerson extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private ArrayList<String> data;
	private ArrayList<String> columns = new ArrayList<String>();

	public TabModelPerson(ArrayList<String> data) {
		this.data = data;
		columns.add("Name");
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
			return data.get(row);
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return columns.get(column);
	}
	
	public ArrayList<String> getData() {
		return data;
	}

	public void up(int index) {
		if (index > 0 && index < data.size()) {
			String element = data.remove(index);
			data.add(index-1, element);
		}
	}

	public void down(int index) {
		if (index >= 0 && index < data.size()-1) {
			String element = data.remove(index);
			data.add(index+1, element);
		}
	}

	public String getRow(int row) {
		return data.get(row);
	}

	public void addRow(String row) {
		data.add(row);		
	}

	public void deleteRow(int row) {
		data.remove(row);
	}

	public void changeRow(int row, String rowData) {
		data.set(row,  rowData);
	}

}
