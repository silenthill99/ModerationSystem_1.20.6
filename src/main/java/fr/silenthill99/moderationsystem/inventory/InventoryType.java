package fr.silenthill99.moderationsystem.inventory;

import fr.silenthill99.moderationsystem.inventory.hook.ReportInventory;

import java.util.Arrays;
import java.util.List;

public enum InventoryType {
    REPORTS(new ReportInventory())
    ;
    private final AbstractInventory<?> inv;

    InventoryType(AbstractInventory<?> inv) {
        this.inv = inv;
    }

    public AbstractInventory<?> getInv() {
        return this.inv;
    }

    public static List<InventoryType> getList() {
        return Arrays.asList(values());
    }
}
