package fr.silenthill99.moderationsystem.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryManager implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        InventoryType.getList().forEach((inv) -> inv.getInv().onJoin(event));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        InventoryType.getList().forEach((inv) -> inv.getInv().onQuit(event));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        InventoryType.getList().forEach((inv) -> inv.getInv().onInteract(event));
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        InventoryType.getList().forEach((inv) -> inv.getInv().onInteractEntity(event));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player) || event.getClickedInventory() == null)
            return;
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof SilenthillHolder nh) {
            for (InventoryType type : InventoryType.values()) {
                AbstractInventory inv = type.getInv();
                if (inv.isInstance(nh)) {
                    event.setCancelled(true);
                    ItemStack item = event.getCurrentItem();
                    if (item != null) {
                        if (item.isSimilar(AbstractInventory.CLOSE)) {
                            player.closeInventory();
                        } else {
                            inv.manageInventory(event, item, player, nh);
                        }
                    } else {
                        inv.voidInventory(event, player, nh);
                    }
                    return;
                }
            }
        } else if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && event.getInventory().getHolder()
                instanceof SilenthillHolder nh) {
            for (InventoryType type : InventoryType.values()) {
                AbstractInventory inv = type.getInv();
                if (inv.isInstance(nh)) {
                    event.setCancelled(true);
                    inv.moveFromInventory(event, event.getInventory(), player, nh);
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player))
            return;
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof SilenthillHolder nh) {
            for (InventoryType type : InventoryType.values()) {
                AbstractInventory<?> inv = type.getInv();
                if (inv.isInstance(nh)) {
                    inv.onClose(player, event);
                }
            }
        }
    }

    public static void openInventory(Player player, InventoryType type, Object... args) {
        type.getInv().openInventory(player, args);
    }
}
