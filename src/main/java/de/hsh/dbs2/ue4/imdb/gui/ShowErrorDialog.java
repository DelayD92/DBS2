package de.hsh.dbs2.ue4.imdb.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ShowErrorDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTextArea txtError;

	private JButton btnOk;
	
	public ShowErrorDialog(String msg, Exception e) {
		
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));

		setTitle(msg);
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
		txtError = new JTextArea(sw.toString());
		JScrollPane scroll = new JScrollPane(txtError);
		scroll.setPreferredSize(new Dimension(800, 400));
		layout.setConstraints(scroll, gbc);
		add(scroll);


		gbc.gridx = 0;
		gbc.gridy = 1;
		btnOk = new JButton("OK");
		layout.setConstraints(btnOk, gbc);
		add(btnOk);
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		this.setResizable(false);
		this.setSize(new Dimension(800, 400));

		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}


}
