package fr.silenthill99.moderationsystem;

import fr.silenthill99.moderationsystem.commands.ModerateurCommand;
import fr.silenthill99.moderationsystem.commands.ReportCommand;
import fr.silenthill99.moderationsystem.inventory.InventoryManager;
import fr.silenthill99.moderationsystem.listener.Events;
import fr.silenthill99.moderationsystem.manager.PlayerManager;
import fr.silenthill99.moderationsystem.mysql.DatabaseManager;
import fr.silenthill99.moderationsystem.mysql.Reports;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() {
        return instance;
    }
    private Reports reports;

    public HashMap<UUID, PlayerManager> players = new HashMap<>();
    public ArrayList<UUID> moderateurs = new ArrayList<>();
    private HashMap<UUID, Location> freezedPlayers;

    DatabaseManager db;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        db = new DatabaseManager();
        getLogger().info("Le plugin est opérationnel !");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryManager(), this);
        pm.registerEvents(new Events(), this);
        getCommand("mod").setExecutor(new ModerateurCommand());
        getCommand("report").setExecutor(new ReportCommand());
        freezedPlayers = new HashMap<>();
        reports = new Reports();
    }

    @Override
    public void onDisable() {
        db.close();
    }

    public DatabaseManager getDatabaseManager() {
        return db;
    }

    public HashMap<UUID, Location> getFreezedPlayers() {
        return freezedPlayers;
    }

    public boolean isFreeze(Player player) {
        return freezedPlayers.containsKey(player.getUniqueId());
    }

    public Reports getReports() {
        return reports;
    }
}
