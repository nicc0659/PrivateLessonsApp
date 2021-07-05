package dao;

import java.sql.*;

public class DAO {

    public static final String url = "jdbc:mysql://localhost:3306/bella";
    public static final String user = "root";
    public static final String password = "";

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    public static void closeStatementResult(PreparedStatement ps, ResultSet rs) {
        /* closing the given PreparedStatement */
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /* closing the given ResultSet */
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
