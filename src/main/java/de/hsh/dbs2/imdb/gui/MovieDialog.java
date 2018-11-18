package de.hsh.dbs2.imdb.gui;

import de.hsh.dbs2.imdb.logic.dto.CharacterDTO;
import de.hsh.dbs2.imdb.logic.dto.MovieDTO;
import de.hsh.dbs2.util.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JLabel labId;
	private JLabel txtId;

	private JLabel labTitle;
	private JTextField txtTitle;

	private JLabel labYear;
	private JTextField txtYear;

	private JLabel labType;
	private JTextField txtType;

	private JLabel labGenre;
	private JList<String> lstGenre;
	private JScrollPane scrGenre; 
	
	private JPanel pnlButtons;
	private JButton btnOk;
	private JButton btnCancel;
	
	private JTable tabCharacters;
	private JScrollPane scrCharacters;

	private JButton btnUp;
	private JButton btnDown;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;

	private String outcome = "cancel";

	private MovieDialogCallback callback;
	private TabModelCharacter tabModel;

	private MovieDTO movie;
	
	public MovieDialog(MovieDialogCallback callback, List<String> allGenres) {
		this.callback = callback;
		
		setTitle("Movie");
		setSize(400, 200);
		setModal(true);

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(2, 2, 2, 2);

		gbc.gridx = 0;
		gbc.gridy = 0;
		labId = new JLabel("Id:");
		layout.setConstraints(labId, gbc);
		add(labId);

		gbc.gridx = 1;
		gbc.gridy = 0;
		txtId = new JLabel("");
		layout.setConstraints(txtId, gbc);
		add(txtId);

		gbc.gridx = 0;
		gbc.gridy = 1;
		labTitle = new JLabel("Title:");
		layout.setConstraints(labTitle, gbc);
		add(labTitle);

		gbc.gridx = 1;
		gbc.gridy = 1;
		txtTitle = new JTextField(50);
		layout.setConstraints(txtTitle, gbc);
		add(txtTitle);

		gbc.gridx = 0;
		gbc.gridy = 2;
		labYear = new JLabel("Year:");
		layout.setConstraints(labYear, gbc);
		add(labYear);

		gbc.gridx = 1;
		gbc.gridy = 2;
		txtYear = new JTextField(4);
		layout.setConstraints(txtYear, gbc);
		add(txtYear);

		gbc.gridx = 0;
		gbc.gridy = 3;
		labType = new JLabel("Type:");
		layout.setConstraints(labType, gbc);
		add(labType);

		gbc.gridx = 1;
		gbc.gridy = 3;
		txtType = new JTextField(1);
		layout.setConstraints(txtType, gbc);
		add(txtType);

		gbc.gridx = 0;
		gbc.gridy = 4;
		labGenre = new JLabel("Genres:");
		layout.setConstraints(labGenre, gbc);
		add(labGenre);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridheight = 4;
		lstGenre = new JList<String>();
		scrGenre = new JScrollPane(lstGenre);
		layout.setConstraints(scrGenre, gbc);
		add(scrGenre);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridheight = 1;
		gbc.gridwidth = 4;
		pnlButtons = new JPanel();
		layout.setConstraints(pnlButtons, gbc);
		add(pnlButtons);

		btnOk = new JButton("OK");
		pnlButtons.add(btnOk);
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outcome = "ok";
				updateMovie();
				setVisible(false);
			}
		});

		btnCancel = new JButton("Cancel");
		pnlButtons.add(btnCancel);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outcome = "cancel";
				setVisible(false);
			}
		});

		lstGenre.setListData(allGenres.toArray(new String[0]));

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 8;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
		tabCharacters = new JTable();
		tabCharacters.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabCharacters.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrCharacters = new JScrollPane(tabCharacters);
		layout.setConstraints(scrCharacters, gbc);
		add(scrCharacters);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		btnUp = new JButton("Up");
		layout.setConstraints(btnUp, gbc);
		add(btnUp);

		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveCharacterUp();
			}
		});

		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		btnDown = new JButton("Down");
		layout.setConstraints(btnDown, gbc);
		add(btnDown);

		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveCharacterDown();
			}
		});

		gbc.gridx = 3;
		gbc.gridy = 2;
		btnAdd = new JButton("Add");
		layout.setConstraints(btnAdd, gbc);
		add(btnAdd);

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCharacter();
			}
		});

		gbc.gridx = 3;
		gbc.gridy = 3;
		btnEdit = new JButton("Edit");
		layout.setConstraints(btnEdit, gbc);
		add(btnEdit);

		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCharacter();
			}
		});

		gbc.gridx = 3;
		gbc.gridy = 4;
		btnDelete = new JButton("Delete");
		layout.setConstraints(btnDelete, gbc);
		add(btnDelete);

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCharacter();
			}
		});

		tabCharacters.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					editCharacter();
				}
			}
		});

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	public void setMovie(MovieDTO movie) {
        Log.debug(movie);
		this.movie = movie;
		txtId.setText(movie.getId() == null ? "" : ("" + movie.getId()) );
		txtTitle.setText(movie.getTitle());
		txtYear.setText("" + movie.getYear());
		txtType.setText(("" + movie.getType()).trim());

		int [] indices = new int[movie.getGenres().size()];
		int j = 0;
		for (String genre : movie.getGenres()) {
			for (int i = 0; i < lstGenre.getModel().getSize(); i++) {
				if (lstGenre.getModel().getElementAt(i).equals(genre))
					indices[j++] = i;
			}
		}
		lstGenre.setSelectedIndices(indices);
		
		tabModel = new TabModelCharacter(movie.getCharacters());
		tabCharacters.setModel(tabModel);

		tabCharacters.getColumnModel().getColumn(0).setPreferredWidth(150);
		tabCharacters.getColumnModel().getColumn(1).setPreferredWidth(150);
		tabCharacters.getColumnModel().getColumn(2).setPreferredWidth(150);

		pack();
	}

	public void updateMovie() {
		// Werte aus den Dialogfeldern in den Movie uebernehmen
		movie.setTitle(txtTitle.getText());
		movie.setYear(Integer.parseInt(txtYear.getText()));
		movie.setType(txtType.getText());

		// In der Liste selektiere Genres in die Liste im Movie uebernehmen
		Set<String> genres = new HashSet<String>();
		for (int i : lstGenre.getSelectedIndices()) {
			String genre = lstGenre.getModel().getElementAt(i);
			genres.add(genre);
		}
		movie.setGenres(genres);
	}

	public String getOutcome() {
		return outcome;
	}


	public void moveCharacterUp() {
		int sel = tabCharacters.getSelectedRow();
		if (sel > 0) {
			tabModel.up(sel);
			tabModel.fireTableDataChanged();
			tabCharacters.setRowSelectionInterval(sel - 1, sel - 1);
		}
	}
	
	public void moveCharacterDown() {
		int sel = tabCharacters.getSelectedRow();
		if (sel < tabCharacters.getRowCount() - 1) {
			tabModel.down(tabCharacters.getSelectedRow());
			tabModel.fireTableDataChanged();
			tabCharacters.setRowSelectionInterval(sel + 1, sel + 1);
		}
	}
	
	public void addCharacter() {
		CharacterDTO character = callback.addCharacter();
		if (character != null) {
			tabModel.addRow(character);
			tabModel.fireTableDataChanged();
		}
	}
	
	public void editCharacter() {
		int row = tabCharacters.getSelectedRow();
		CharacterDTO mc = tabModel.getRow(row);
		callback.editCharacter(mc);
		tabModel.fireTableDataChanged();
	}

	public void deleteCharacter() {
		int row = tabCharacters.getSelectedRow();
		if (row >= 0) {
			tabModel.deleteRow(tabCharacters.getSelectedRow());
			tabModel.fireTableDataChanged();
			if (row >= tabModel.getRowCount())
				row--;
			if (row >= 0)
				tabCharacters.setRowSelectionInterval(row, row);
		}
	}
}
