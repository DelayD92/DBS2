package de.hsh.dbs2.ue4.imdb;

import de.hsh.dbs2.ue4.imdb.gui.SearchMovieDialog;
import de.hsh.dbs2.ue4.imdb.gui.SearchMovieDialogCallback;
import de.hsh.dbs2.ue3.factory.MovieDbEntityManagerFactory;
import de.hsh.dbs2.util.Log;

import javax.swing.*;

public class Starter  {

    private static final Object o = new Object();

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
        Log.setLogLevel(Log.LogLevel.DEBUG);
        SwingUtilities.invokeLater(() -> new Starter().run());
	}

	private void run() {
		SearchMovieDialogCallback callback = new SearchMovieDialogCallback();
		SearchMovieDialog sd = new SearchMovieDialog(callback, MovieDbEntityManagerFactory::shutdown);
		sd.setVisible(true);
	}
}
