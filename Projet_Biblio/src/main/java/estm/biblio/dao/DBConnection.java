package estm.biblio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/biblio_db";
    private static final String USER = "root";
    private static final String PASS = ""; // Vide pour WAMP
    private static Connection conn;

    public static Connection getConn() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}