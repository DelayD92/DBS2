package de.hsh.dbs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {

        static String driverURI = "oracle.jdbc.driver.OracleDriver";
        static String user = "a0y-g2u-u1";
        static String pwd = "testit2_";

        public DBConnect() throws SQLException {
            String query = "";
            Statement stmt = null;
            ResultSet rs = null;
            Connection con = null;

            try {
                Class.forName(driverURI);
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:db01", user, pwd);
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next()) {
                    ;
                }
            } catch (ClassNotFoundException | SQLException var9) {
                var9.printStackTrace();
            } finally {
                rs.close();
                stmt.close();
            }
        }

    }

}
