package fr.silenthill99.moderationsystem.commands;

import fr.silenthill99.moderationsystem.Main;
import fr.silenthill99.moderationsystem.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ModerateurCommand implements CommandExecutor {

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s,
                             @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Seul un joueur peut executer cette commande !");
            return false;
        }


        if (PlayerManager.isInModerationMod(player)) {
            PlayerManager.getFromPlayer(player).destroy();
        } else {
            new PlayerManager(player).init();
        }

        return true;
    }
}
