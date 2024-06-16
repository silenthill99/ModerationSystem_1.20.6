package fr.silenthill99.moderationsystem.managers;

import fr.silenthill99.moderationsystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

@SuppressWarnings("deprecation")
public class FreezeRunnable extends BukkitRunnable {

    public static final Main main = Main.getInstance();
    private int t = 0;

    @Override
    public void run() {
        for (UUID uuid : main.getFreezedPlayers().keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Location loc = main.getFreezedPlayers().get(uuid);
                player.teleport(loc);
                if (t == 5) {
                    player.sendMessage(ChatColor.GREEN + "Vous avez été freeze");
                }
            }
        }

        if (t == 5) {
            t = 0;
        }

        t++;
    }
}
