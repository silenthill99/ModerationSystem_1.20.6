package fr.silenthill99.moderationsystem.mysql;

import fr.silenthill99.moderationsystem.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final Main main = Main.getInstance();
    private final DbCredentials dbCredentials;
    private Connection connection;

    public DbConnection(DbCredentials dbCredentials) {
        this.dbCredentials = dbCredentials;
        connect();
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection(this.dbCredentials.toURI(), this.dbCredentials.getUser(),
                    this.dbCredentials.getPass());
            main.getLogger().info("Connection établie avec la BDD !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws SQLException {
        if (this.connection != null) {
            if (!this.connection.isClosed()) {
                connection.close();
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.connection != null) {
            if (!this.connection.isClosed()) {
                return this.connection;
            }
        }
        connect();
        return this.connection;
    }

}
