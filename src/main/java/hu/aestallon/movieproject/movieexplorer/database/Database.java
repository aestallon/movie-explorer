package hu.aestallon.movieproject.movieexplorer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class Database {
    private static final String DB_URL   = "jdbc:derby://localhost:1527/MOVIE";
    private static final String USER     = "app";
    private static final String PASSWORD = "derby";

    static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    static int executeDataManipulation(String sql, Object... parameters) {
        if (sql == null || parameters == null) {
            throw new IllegalArgumentException();
        }

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                stmt.setObject(i + 1, parameters[i]);
            }
            return stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }
}
