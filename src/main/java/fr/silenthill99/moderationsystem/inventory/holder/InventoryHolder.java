package fr.silenthill99.moderationsystem.inventory.holder;

import fr.silenthill99.moderationsystem.inventory.SilenthillHolder;
import org.bukkit.entity.Player;

public class InventoryHolder extends SilenthillHolder {

    private final Player target;

    public InventoryHolder(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }
}
