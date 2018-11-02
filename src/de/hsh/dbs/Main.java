package de.hsh.dbs;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static User u = new User("tnn-867-u1", "testit2_");


    public static void main(String args[]) throws SQLException {
        String query = "SELECT * FROM Movie";
        Statement stmt = null;
        ResultSet rs = null;
        Connection con;

        //FilmID Abfrage

        //Testkrücke
        String argsDummy="1";
        int filmID = Integer.parseInt(argsDummy);


        //Datenbankverbindung mit Statement
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:db01", u.getUsername(), u.getPassword());
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

                while(rs.next()){
                    String title= rs.getString("title");
                    System.out.println("Genre: " + title);
                }
        } catch ( SQLException var9) {
            var9.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
        }

    }
}
