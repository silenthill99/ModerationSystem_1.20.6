package fr.silenthill99.moderationsystem.mysql;

import fr.silenthill99.moderationsystem.Main;
import fr.silenthill99.moderationsystem.manager.Report;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Reports {
    Connection connection;

    {
        try {
            connection = Main.getInstance().getDatabaseManager().getDbConnection().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reports() {
    }

    public void report(Report report) {

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO reports (uuid, date, auteur, raison) VALUES (?, ?, ?, ?)");
                statement.setString(1, report.getUuid().toString());
                statement.setString(2, report.getDate());
                statement.setString(3, report.getAuthor());
                statement.setString(4, report.getReason());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void remove(int id) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM reports WHERE `#` = '?'");
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    rs.deleteRow();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Report getReport(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM reports WHERE `#` = '?'");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            Report report = new Report(UUID.fromString(rs.getString("uuid")), rs.getString("date"), rs.getString("auteur"), rs.getString("raison"));
            if (rs.next()) {
                return report;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Integer> getFromUUID(UUID uuid) {
        List<Integer> ids = new ArrayList<>();

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reports WHERE uuid = '"+uuid.toString()+"'");
//                preparedStatement.setString(1, uuid.toString());
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    ids.add(rs.getInt("#"));
                    System.out.println(rs.getInt("#"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return ids;
    }
}
