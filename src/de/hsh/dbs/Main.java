package de.hsh.dbs;

import javax.xml.transform.Result;
import java.sql.*;

public class Main {

    private User u = new User("", "");
     static String driverURI = "oracle.jdbc.driver.OracleDriver";


    public static void main(String args[]) throws SQLException {

        try {
            Class.forName(driverURI);
            Connection con = DiverManager.getConnection("jdbc:oracle:thin@localhost:1521:db01", u.getUsername(), u.getPassword());
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while( rs.next() ){

            }
        } catch (ClassNotFoundException | SQLException var9) {
            var9.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
        }

    }
}
