package fr.silenthill99.moderationsystem.inventory.hook;

import fr.silenthill99.moderationsystem.CustomItems;
import fr.silenthill99.moderationsystem.manager.PlayerManager;
import fr.silenthill99.moderationsystem.inventory.AbstractInventory;
import fr.silenthill99.moderationsystem.inventory.holder.InventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryInv extends AbstractInventory<InventoryHolder> {
    public InventoryInv() {
        super(InventoryHolder.class);
    }

    @Override
    public void openInventory(Player player, Object... args) {
        Player target = (Player) args[0];

        InventoryHolder holder = new InventoryHolder(target);

        Inventory inv = createInventory(holder, 45, target.getName() + " > Inventaire");

        for (int i = 0; i < 36; i++) {
            if (target.getInventory().getItem(i) != null) {
                inv.setItem(i, target.getInventory().getItem(i));
            }
        }

        inv.setItem(36, target.getInventory().getHelmet());
        inv.setItem(37, target.getInventory().getChestplate());
        inv.setItem(38, target.getInventory().getLeggings());
        inv.setItem(39, target.getInventory().getBoots());
        inv.setItem(40, target.getInventory().getItemInOffHand());

        player.openInventory(inv);
    }

    @Override
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!PlayerManager.isInModerationMod(player)) return;
        if (!(event.getRightClicked() instanceof Player target)) return;

        if (item.isSimilar(CustomItems.INVSEE.getItem())) {
            openInventory(player, target);
        }
    }
}
