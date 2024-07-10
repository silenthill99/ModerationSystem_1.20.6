package fr.silenthill99.moderationsystem.inventory.hook;

import fr.silenthill99.moderationsystem.ItemBuilder;
import fr.silenthill99.moderationsystem.Main;
import fr.silenthill99.moderationsystem.inventory.AbstractInventory;
import fr.silenthill99.moderationsystem.inventory.holder.ReportHolder;
import fr.silenthill99.moderationsystem.manager.Report;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class ReportInventory extends AbstractInventory<ReportHolder> {
    public ReportInventory() {
        super(ReportHolder.class);
    }


    @Override
    public void openInventory(Player player, Object... args) {
        Player target = (Player) args[0];

        ReportHolder holder = new ReportHolder(target);

        ItemStack forcefield = new ItemBuilder(Material.IRON_SWORD).setName(ChatColor.RED + "ForceField").toItemStack();
        ItemStack spam_bow = new ItemBuilder(Material.BOW).setName(ChatColor.RED + "SpamBow").toItemStack();

        Inventory inv = createInventory(holder, 18,
                ChatColor.AQUA + "Report: " + ChatColor.RED + target.getName());
        inv.setItem(0, forcefield);
        inv.setItem(1, spam_bow);
        player.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent event, ItemStack current, Player player, ReportHolder holder) {
        Player target = holder.getTarget();

        if (target == null) return;


        player.closeInventory();
        player.sendMessage(ChatColor.GREEN + "Vous avez bien signalé ce joueur !");

        Main.getInstance().getReports().add(new Report(target.getUniqueId(), player.getName(),
                current.getItemMeta().getDisplayName().substring(2)));
        sendToModos(current.getItemMeta().getDisplayName(), target.getName());

    }

    private void sendToModos(String displayName, String targetName) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("mod.receive")) {
                players.sendMessage(ChatColor.AQUA + "Le joueur " + targetName + " a été signalé pour : " + displayName);
            }
        }
    }
}
