package fr.silenthill99.moderationsystem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings({"unused", "deprecation"})
public enum CustomItems {
    INVSEE(new ItemBuilder(Material.PAPER).setName(ChatColor.YELLOW + "Voir l'inventaire")
            .addLore(ChatColor.GRAY + "Clique droit sur un joueur", ChatColor.GRAY + "pour voir son inventaire")
            .toItemStack()),
    REPORTS(new ItemBuilder(Material.BOOK).setName(ChatColor.GOLD + "Voir les signalements")
            .addLore(ChatColor.GRAY + "Clique droit sur un joueur", ChatColor.GRAY + "pour voir ses signalements")
            .toItemStack()),
    FREEZE(new ItemBuilder(Material.PACKED_ICE).setName(ChatColor.AQUA + "Freeze")
            .addLore(ChatColor.GRAY + "Clique droit sur un joueur", ChatColor.GRAY + "pour voir ses signalements")
            .toItemStack()),
    KB_TESTER(new ItemBuilder(Material.STICK).setName(ChatColor.LIGHT_PURPLE + "Test de recul")
            .addLore(ChatColor.GRAY + "Clique gauche sur un joueur", ChatColor.GRAY + "pour tester son recul")
            .toItemStack()),
    KILLER(new ItemBuilder(Material.BLAZE_ROD).setName(ChatColor.RED + "Tueur de joueur")
            .addLore(ChatColor.GRAY + "Clique droit sur un joueur", ChatColor.GRAY + "pour le tuer")
            .toItemStack()),
    TP_RANDOM(new ItemBuilder(Material.ARROW).setName(ChatColor.GREEN + "Téléportation aléatoire")
            .addLore(ChatColor.GRAY + "Clique droit pour se téléporter", ChatColor.GRAY + "aléatoirement sur un joueur")
            .toItemStack())
    ;
    private final ItemStack item;

    CustomItems(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}
