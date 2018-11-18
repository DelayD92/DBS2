package de.hsh.dbs2.imdb;

import de.hsh.dbs2.core.Config;
import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.imdb.gui.SearchMovieDialog;
import de.hsh.dbs2.imdb.gui.SearchMovieDialogCallback;
import de.hsh.dbs2.util.Log;

import javax.swing.*;

public class Starter {

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			    try {
                    new Starter().bootstrap(args).run();
                } catch (Exception e) {
                    Log.error(e);
                }
			}
		});
	}

    private Starter bootstrap(String... args) throws Exception {
        Config config = Config.Loader.loadConfigs();
        Log.setLogLevel(config.logLevel);
        Database.init(config.db);
        return this;
    }

	public void run() {
		SearchMovieDialogCallback callback = new SearchMovieDialogCallback();
		SearchMovieDialog sd = new SearchMovieDialog(callback);
		sd.setVisible(true);
	}
}
