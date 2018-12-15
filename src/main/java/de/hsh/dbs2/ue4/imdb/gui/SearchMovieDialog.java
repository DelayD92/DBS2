package de.hsh.dbs2.ue4.imdb.gui;

import de.hsh.dbs2.ue4.imdb.logic.dto.MovieDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class SearchMovieDialog extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txtSearch;
	private JButton btnSearch;

	private JTable tabResult;
	private JScrollPane scrResult;
	
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;

	private TabModelMovie tabModel;
	
	private SearchMovieDialogCallback callback;
	
	public SearchMovieDialog(SearchMovieDialogCallback callback, Runnable onCloseCb) {
		
		this.callback = callback;
		
		setTitle("Search");
		setSize(200, 200);

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);

		GridBagConstraints gbc = new GridBagConstraints();

		// Festlegen, dass die GUI-Elemente die Gitterfelder in
		// waagerechter Richtung ausfuellen:
		gbc.fill = GridBagConstraints.BOTH;

		// Die Abstaende der einzelnen GUI-Elemente zu den gedachten
		// Gitterlinien festgelegen:
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.gridx = 0;
		gbc.gridy = 0;
		txtSearch = new JTextField(50);
		layout.setConstraints(txtSearch, gbc);
		add(txtSearch);

		gbc.gridx = 1;
		gbc.gridy = 0;
		btnSearch = new JButton("Search");
		layout.setConstraints(btnSearch, gbc);
		add(btnSearch);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 8;
		tabResult = new JTable();
		tabResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrResult = new JScrollPane(tabResult);
		layout.setConstraints(scrResult, gbc);
		add(scrResult);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		btnAdd = new JButton("Add");
		layout.setConstraints(btnAdd, gbc);
		add(btnAdd);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		btnEdit = new JButton("Edit");
		layout.setConstraints(btnEdit, gbc);
		add(btnEdit);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		btnDelete = new JButton("Delete");
		layout.setConstraints(btnDelete, gbc);
		add(btnDelete);

		tabResult.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					editMovie();
				}
			}
		});

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMovie();
			}
		});

		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editMovie();
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMovie();
			}
		});
		
		WindowListener windowListener = new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
                new Thread(onCloseCb).run();
				System.exit(0);
			}
		};
		this.addWindowListener(windowListener);

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	private MovieDTO getSelection() {
		if (tabResult.getSelectedRow() != -1)
			return tabModel.getRow(tabResult.getSelectedRow());
		else
			return null;
	}

	private void addMovie() {
		callback.addMovie();
		search();
	}
	
	private void editMovie() {
		if (getSelection() == null) 
			JOptionPane.showMessageDialog(null, "Bitte zuerst einen Film auswaehlen!");
		else {
			callback.editMovie(getSelection().getId());
			search();
		}
	}
	
	private void deleteMovie() {
		callback.deleteMovie(getSelection().getId());
		search();
	}
	
	private void search() {
		int row = tabResult.getSelectedRow();

		List<MovieDTO> result = callback.runSearch(txtSearch.getText());
		if (result != null) {
			tabModel = new TabModelMovie(result);
			tabResult.setModel(tabModel);
			tabResult.getColumnModel().getColumn(0).setPreferredWidth(60);
			tabResult.getColumnModel().getColumn(1).setPreferredWidth(300);
			tabResult.getColumnModel().getColumn(2).setPreferredWidth(60);
			tabResult.getColumnModel().getColumn(3).setPreferredWidth(60);
			
			tabResult.getSelectionModel().setSelectionInterval(row,  row);
		}
		pack();
	}
}
