package fr.silenthill99.moderationsystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
    }

    public static PlayerManager getFromPlayer(Player player) {
        return main.players.get(player.getUniqueId());
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void destroy() {
        main.players.remove(player.getUniqueId());
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
        return CustomFiles.MODERATION.getConfig().getStringList("uuid").contains(player.getUniqueId().toString());
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
