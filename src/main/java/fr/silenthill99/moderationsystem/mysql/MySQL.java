package fr.silenthill99.moderationsystem.mysql;

import fr.silenthill99.moderationsystem.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class MySQL {

    public static final Main main = Main.getInstance();

    private Connection getConnection() throws SQLException {
        return main.getDatabaseManager().getDbConnection().getConnection();
    }

    public void createTables() {
        update("CREATE TABLE IF NOT EXISTS  reports (" +
                "`#` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "pseudo VARCHAR(255), " +
                "uuid VARCHAR(255), " +
                "date VARCHAR(255), " +
                "auteur VARCHAR(255), " +
                "raison VARCHAR(255))");
    }

    public void update(String qry) {
        try (Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement(qry);
            s.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object query(String qry, Function<ResultSet, Object> consumer) {
        try (Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement(qry);
            ResultSet rs = s.executeQuery();
            return consumer.apply(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void query(String qry, Consumer<ResultSet> consumer) {
        try (Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement(qry);
            ResultSet rs = s.executeQuery();
            consumer.accept(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
