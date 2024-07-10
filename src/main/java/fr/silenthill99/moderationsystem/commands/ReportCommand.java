package fr.silenthill99.moderationsystem.commands;

import fr.silenthill99.moderationsystem.inventory.InventoryManager;
import fr.silenthill99.moderationsystem.inventory.InventoryType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

@SuppressWarnings("deprecation")
public class ReportCommand implements CommandExecutor {
    private final HashMap<Player, Long> refractorCooldown = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s,
                             @NotNull String[] args) {


        if (!(sender instanceof Player player)) {
            sender.sendMessage("Seul un joueur peut éxécuter cette commande !");
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Veuillez saisir le pseudo d'un joueur !");
            return false;
        }

        String targetName = args[0];

        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            player.sendMessage(ChatColor.RED + "Ce joueur n'est pas connecté ou n'existe pas !");
            return false;
        }

        if (refractorCooldown.containsKey(player)) {
            long time = (System.currentTimeMillis() - refractorCooldown.get(player))/1000;
            if (time < 120) {
                player.sendMessage("Merci de patienter entre chaque signalements !");
                return true;
            } else {
                refractorCooldown.remove(player);
            }
        }

        refractorCooldown.put(player, System.currentTimeMillis());

        InventoryManager.openInventory(player, InventoryType.REPORTS, target);

        return true;
    }
}
