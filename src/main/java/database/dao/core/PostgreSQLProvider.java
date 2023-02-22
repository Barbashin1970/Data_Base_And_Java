package database.dao.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLProvider {

    protected final static String URL = "jdbc:postgresql://localhost:5432/practicum";
    protected String tableName;

    protected Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, "postgres", "postgres");
        } catch (SQLException e) {
            System.out.println("Can not get connection: " + e.getMessage());
        }
        return null;
    }

}