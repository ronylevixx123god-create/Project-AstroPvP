package net.astropvp.inventories;

import me.ifamasssxd.factionupgrades.utils.SlotUtils;
import net.astropvp.Carnage;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class CarnageInventory {
    public static Inventory get() {
        Inventory inventory = Bukkit.createInventory(null, SlotUtils.fitSlots(Carnage.getInstance().getCarnageManager().getCarnageItems().size()), "Carnage");
        for(int i = 0; i < Carnage.getInstance().getCarnageManager().getCarnageItems().size(); i++) {
            if(Carnage.getInstance().getCarnageManager().getCarnageItems().get(i).getAmount() < 1) {
                Carnage.getInstance().getCarnageManager().getCarnageItems().get(i).setAmount(1);
            }
            inventory.setItem(i, Carnage.getInstance().getCarnageManager().getCarnageItems().get(i));
        }
        return inventory;
    }
}
