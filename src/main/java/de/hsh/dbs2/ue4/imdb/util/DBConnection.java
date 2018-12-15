package de.hsh.dbs2.ue4.imdb.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	private static Connection conn;
	
	static {
		conn = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@dboracleserv.inform.hs-hannover.de:1521:db01", "user", "pwd");
			conn.setAutoCommit(false);
			System.out.println("Connect durchgefuehrt ....");
		} catch (Exception e) {
			System.err.println("Error while connecting to database");
			e.printStackTrace();
			System.exit(1);
		} 
	}
		
	public static Connection getConnection() {
		return conn;
	}
}
