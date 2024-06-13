package fr.silenthill99.moderationsystem.mysql;

import java.sql.SQLException;

public class DatabaseManager {

    private final DbConnection dbConnection;

    public DatabaseManager() {
        dbConnection = new DbConnection(new DbCredentials("minecraft118.omgserv.com", "minecraft_235640",
                "Mylene.10", "minecraft_235640", 3306));
    }

    public DbConnection getDbConnection() {
        return dbConnection;
    }

    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
