package de.hsh.dbs2.imdb.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Dialog um einen Character eines Filmes zu aendern.
 * Es kann eine neue Person (d.h. ein neuer Schauspieler) ausgewaehlt werden,
 * und es kann Name und Alias des Characters veraendert werden.
 * Die Position wird indirekt ueber die Sortierung der Liste im Movie-Dialog 
 * festgelegt.
 */
public class CharacterDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JLabel labCharacter;
	private JTextField txtCharacter;

	private JLabel labAlias;
	private JTextField txtAlias;

	private JLabel labPlayer;
	private JTextField txtPlayer;
	private JList<String> lstPersons;
	private JScrollPane scrPersons;

	private JPanel pnlButtons;
	private JButton btnOk;
	private JButton btnCancel;

	private String outcome = "cancel";

	private CharacterDialogCallback callback;
	
	public CharacterDialog(CharacterDialogCallback callback) {
		this.callback = callback;
		
		setTitle("Movie Character");
		setSize(200, 200);
		setModal(true);

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
		labCharacter = new JLabel("Character:");
		layout.setConstraints(labCharacter, gbc);
		add(labCharacter);

		gbc.gridx = 1;
		gbc.gridy = 0;
		txtCharacter = new JTextField(50);
		layout.setConstraints(txtCharacter, gbc);
		add(txtCharacter);

		gbc.gridx = 0;
		gbc.gridy = 1;
		labAlias = new JLabel("Alias:");
		layout.setConstraints(labAlias, gbc);
		add(labAlias);

		gbc.gridx = 1;
		gbc.gridy = 1;
		txtAlias = new JTextField(50);
		layout.setConstraints(txtAlias, gbc);
		add(txtAlias);

		gbc.gridx = 0;
		gbc.gridy = 3;
		labPlayer = new JLabel("Player:");
		layout.setConstraints(labPlayer, gbc);
		add(labPlayer);

		gbc.gridx = 1;
		gbc.gridy = 3;
		txtPlayer = new JTextField(50);
		layout.setConstraints(txtPlayer, gbc);
		add(txtPlayer);

		txtPlayer.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				loadPersonList();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				loadPersonList();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				loadPersonList();
			}
		});
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridheight = 4;
		gbc.gridwidth = 2;
		lstPersons = new JList<String>();
		scrPersons = new JScrollPane(lstPersons);
		layout.setConstraints(scrPersons, gbc);
		add(scrPersons);
		
		lstPersons.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (lstPersons.getSelectedIndex() != -1) {
					String player = lstPersons.getSelectedValue();
					setPlayer(player);
				}
			}
		});
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		pnlButtons = new JPanel();
		layout.setConstraints(pnlButtons, gbc);
		add(pnlButtons);

		btnOk = new JButton("OK");
		pnlButtons.add(btnOk);

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outcome = "ok";
				setVisible(false);
			}
		});

		btnCancel = new JButton("Cancel");
		pnlButtons.add(btnCancel);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					outcome = "cancel";
					setVisible(false);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	public void start() {
		pack();
		outcome = "cancel";
		setVisible(true);
	}

	public void setCharacter(String character) {
		txtCharacter.setText(character);
	}
	
	public String getCharacter() {
		return txtCharacter.getText();
	}
	
	public void setAlias(String alias) {
		txtAlias.setText(alias);
	}
	
	public String getAlias() {
		return txtAlias.getText();
	}
	
	public void setPlayer(String player) {
		txtPlayer.setText(player);
	}
	
	public String getPlayer() {
		return txtPlayer.getText();
	}
		
	public String getOutcome() {
		return outcome;
	}

	/**
	 * Neuladen der Personenliste. Wird immer aufgerufen, wenn der Text im Dialogfeld "schauspieler"
	 * geaendert wurde.
	 */
	private void loadPersonList() {
		List<String> p;
		if (txtPlayer.getText().isEmpty()) {
			p = new ArrayList<String>();
		} else {
			p = callback.getPersonList(txtPlayer.getText());
		}
		lstPersons.setListData(p.toArray(new String[0]));
	}


}
