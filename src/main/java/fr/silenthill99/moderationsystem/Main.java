package fr.silenthill99.moderationsystem;

import fr.silenthill99.moderationsystem.commands.ModerateurCommand;
import fr.silenthill99.moderationsystem.commands.ReportCommand;
import fr.silenthill99.moderationsystem.inventory.InventoryManager;
import fr.silenthill99.moderationsystem.listener.Events;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

//    public ArrayList<UUID> moderateurs = new ArrayList<>();
    public HashMap<UUID, PlayerManager> players = new HashMap<>();

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        getLogger().info("Le plugin est opérationnel !");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryManager(), this);
        pm.registerEvents(new Events(), this);
        getCommand("mod").setExecutor(new ModerateurCommand());
        getCommand("report").setExecutor(new ReportCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
