package fr.silenthill99.moderationsystem.commands;

import fr.silenthill99.moderationsystem.CustomFiles;
import fr.silenthill99.moderationsystem.CustomItems;
import fr.silenthill99.moderationsystem.Main;
import fr.silenthill99.moderationsystem.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@SuppressWarnings("deprecation")
public class ModerateurCommand implements CommandExecutor {

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s,
                             @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Seul un joueur peut executer cette commande !");
            return false;
        }

        PlayerManager pm = new PlayerManager(player);

        if (PlayerManager.isInModerationMod(player)) {
            pm = PlayerManager.getFromPlayer(player);
            try {
                CustomFiles.MODERATION.removeValue(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            player.getInventory().clear();
            player.sendMessage(ChatColor.RED + "vous n'êtes à présent plus dans le mode modération");
            if (pm != null) {
                pm.giveInventory();
                pm.destroy();
            }
            player.setAllowFlight(false);
            player.setFlying(false);
            return true;
        }
        pm.init();

        try {
            CustomFiles.MODERATION.addValue(player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        player.sendMessage(ChatColor.GREEN + "Vous êtes à présent dans le mode modération !");
        pm.saveInventory();

        player.setAllowFlight(true);
        player.setFlying(true);

        int slot = 0;
        for (CustomItems items : CustomItems.values()) {
            player.getInventory().setItem(slot++, items.getItem());
        }
        return true;
    }
}
