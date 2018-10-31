package de.hsh.dbs;

import javax.xml.transform.Result;
import java.sql.*;

public class Main {
    static User u = new User("", "");


    public static void main(String args[]) throws SQLException {
        String sql = null;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:db01", u.getUsername(), u.getPassword());
            sql = "SELECT *" + " FROM Movie";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

                while( rs.next() ){
                    String title = rs.getString("title");
                    System.out.println("Movie: " + title);
                }
        } catch ( SQLException var9) {
            var9.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
        }

    }
}
