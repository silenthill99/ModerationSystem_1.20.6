package fr.silenthill99.moderationsystem.listener;

import fr.silenthill99.moderationsystem.CustomItems;
import fr.silenthill99.moderationsystem.Main;
import fr.silenthill99.moderationsystem.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@SuppressWarnings("deprecation")
public class Events implements Listener {
    private final Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (PlayerManager.isInModerationMod(players)) {
                PlayerManager pm = PlayerManager.getFromPlayer(players);
                if (pm.isVanished()) {
                    player.hidePlayer(main, players);
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (PlayerManager.isInModerationMod(player)) {
            PlayerManager.getFromPlayer(player).destroy();
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(PlayerManager.isInModerationMod(player) || main.isFreeze(player));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(PlayerManager.isInModerationMod(player) || main.isFreeze(player));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(PlayerManager.isInModerationMod(player) || main.isFreeze(player));
    }

    @EventHandler
    public void onItemPickup(PlayerAttemptPickupItemEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(PlayerManager.isInModerationMod(player) || main.isFreeze(player));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        event.setCancelled(PlayerManager.isInModerationMod(player) ||
                main.isFreeze(player));
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(PlayerManager.isInModerationMod(player) || main.isFreeze(player));

        ItemStack item = event.getItem();
        Action action = event.getAction();

        if (!PlayerManager.isInModerationMod(player)) return;
        if (item == null) return;

        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) return;

        if (item.isSimilar(CustomItems.TP_RANDOM.getItem())) {
            List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
            list.remove(player);

            if (list.isEmpty()) {
                player.sendMessage(ChatColor.RED + "Il n'y a aucun joueur sur lequel vous téléporter.");
                return;
            }

            Player target = list.get(new Random().nextInt(list.size()));
            player.teleport(target);
            player.sendMessage(ChatColor.GREEN + "Vous avez été téléporté à " + ChatColor.YELLOW +
                    target.getName());
        } else if (item.isSimilar(CustomItems.VANISH.getItem())) {
            PlayerManager mod = PlayerManager.getFromPlayer(player);
            mod.setVanished(!mod.isVanished());
            player.sendMessage(mod.isVanished() ? ChatColor.GREEN + "Vous êtes à présent invisible !" : ChatColor.AQUA +
                    "Vous êtes à présent visible !");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        event.setCancelled(PlayerManager.isInModerationMod(player) || main.isFreeze(player));
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!PlayerManager.isInModerationMod(player)) return;

        if (!(event.getRightClicked() instanceof Player target)) return;

        /**
         * Voir les signalements
         */
        if (item.isSimilar(CustomItems.REPORTS.getItem())) {
            /**
             * TODO
             */
        } else if (item.isSimilar(CustomItems.FREEZE.getItem())) {
            if (!event.getHand().equals(EquipmentSlot.HAND)) return;
            if (main.getFreezedPlayers().containsKey(target.getUniqueId())) {
                main.getFreezedPlayers().remove(target.getUniqueId());
                target.sendMessage(ChatColor.GREEN + "Vous avez été unfreeze !");
                player.sendMessage(ChatColor.GREEN + "Vous avez unfreeze " + ChatColor.YELLOW + target.getName());
            } else {
                main.getFreezedPlayers().put(target.getUniqueId(), target.getLocation());
                target.sendMessage(ChatColor.AQUA + "Vous avez été freeze par " + ChatColor.YELLOW + player.getName());
                player.sendMessage(ChatColor.GREEN + "Vous avez freeze " + ChatColor.YELLOW + target.getName());
            }
        } else if (item.isSimilar(CustomItems.KILLER.getItem())) {
            target.damage(target.getHealth());
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!(event.getDamager() instanceof Player damager)) return;
        ItemStack item = damager.getInventory().getItemInMainHand();

        if (PlayerManager.isInModerationMod(damager)) {
            event.setCancelled(!item.isSimilar(CustomItems.KB_TESTER.getItem()));
        }

        if (main.isFreeze(player)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String prefix = main.getConfig().getString("staff_tchat");

        assert prefix != null;
        if (event.getMessage().startsWith(prefix) && player.hasPermission("moderation.chat")) {
            event.setCancelled(true);
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (players.hasPermission("moderation.chat")) {
                    players.sendMessage(ChatColor.DARK_GRAY + "(" + ChatColor.DARK_GREEN + ChatColor.UNDERLINE +
                            "STAFF CHAT" + ChatColor.DARK_GRAY + ") " + ChatColor.GOLD + player.getName() +
                            ChatColor.WHITE + ": " + ChatColor.YELLOW + event.getMessage().substring(1));
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (main.getFreezedPlayers().containsKey(player.getUniqueId())) {
            event.setTo(event.getFrom());
        }
    }
}
