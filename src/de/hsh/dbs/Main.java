package de.hsh.dbs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

        //jdbc driver setup
        private String driverURI = "oracle.jdbc.driver.OracleDriver";

        //enter username & password for dboracleserv:
        private User user = new User("", "");

        public void main(String args[]) throws SQLException {
            String query = "";
            Statement stmt = null;
            ResultSet rs = null;
            Connection con = null;

            try {
                Class.forName(driverURI);
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:db01", user.getUser(), user.getPwd());
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next()) {

                }
            } catch (ClassNotFoundException | SQLException var9) {
                var9.printStackTrace();
            } finally {
                rs.close();
                stmt.close();
            }
        }

    }


