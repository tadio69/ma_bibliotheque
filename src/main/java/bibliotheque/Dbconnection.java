package bibliotheque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Dbconnection {

    private static String url = "jdbc:postgresql://localhost:5432/bibliothequedb";
    private static String user = "chijou";
    private static String password = "duro@chijou";

    public Dbconnection() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public static void closePreparedStatement(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            // Handle the exception gracefully (e.g., log the error)
            e.printStackTrace();
        }
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            // Handle the exception gracefully (e.g., log the error)
            e.printStackTrace();
        }
    }
}
