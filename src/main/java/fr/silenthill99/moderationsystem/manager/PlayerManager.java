package fr.silenthill99.moderationsystem.manager;

import fr.silenthill99.moderationsystem.CustomItems;
import fr.silenthill99.moderationsystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class PlayerManager {

    private static final Main main = Main.getInstance();
    private final Player player;
    private final ItemStack[] items = new ItemStack[40];
    private boolean vanished;

    public PlayerManager(Player player) {
        this.player = player;
        vanished = false;
    }

    public void init() {
        main.players.put(player.getUniqueId(), this);
        main.moderateurs.add(player.getUniqueId());
        player.sendMessage(ChatColor.GREEN + "Vous êtes à présent dans le mode modération !");
        saveInventory();

        player.setAllowFlight(true);
        player.setFlying(true);

        int slot = 0;
        for (CustomItems items : CustomItems.values()) {
            player.getInventory().setItem(slot++, items.getItem());
        }
    }

    public static PlayerManager getFromPlayer(Player player) {
        return main.players.get(player.getUniqueId());
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void destroy() {
        main.moderateurs.remove(player.getUniqueId());
        main.players.remove(player.getUniqueId());
        player.getInventory().clear();
        player.sendMessage(ChatColor.RED + "vous n'êtes à présent plus dans le mode modération");
        giveInventory();
        player.setAllowFlight(false);
        player.setFlying(false);
        setVanished(false);
    }

    public void saveInventory() {
        for (int slot = 0; slot < 36; slot++) {
            ItemStack item = player.getInventory().getItem(slot);
            if (item != null) {
                items[slot] = item;
            }
        }

        items[36] = player.getInventory().getHelmet();
        items[37] = player.getInventory().getChestplate();
        items[38] = player.getInventory().getLeggings();
        items[39] = player.getInventory().getBoots();

        player.getInventory().clear();

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public void giveInventory() {
        player.getInventory().clear();
        for (int slot = 0; slot < 36; slot++) {
            ItemStack item = getItems()[slot];
            if (item != null) {
                player.getInventory().setItem(slot, item);
            }
        }

        player.getInventory().setHelmet(items[36]);
        player.getInventory().setChestplate(items[37]);
        player.getInventory().setLeggings(items[38]);
        player.getInventory().setBoots(items[39]);
    }

    public static boolean isInModerationMod(Player player) {
        return main.moderateurs.contains(player.getUniqueId());
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;
        if (vanished) {
            Bukkit.getOnlinePlayers().forEach(players -> players.hidePlayer(main, player));
        } else {
            Bukkit.getOnlinePlayers().forEach(players -> players.showPlayer(main, player));
        }
    }
}
