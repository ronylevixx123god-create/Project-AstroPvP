package me.ifamasssxd.cosmicmysterychest.guis;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import me.ifamasssxd.cosmicmysterychest.struct.Filter;
import me.ifamasssxd.factionupgrades.utils.SlotUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterGUI extends CosmicGUI {

    private Player player;

    private Filter filter;


    public FilterGUI(Player player, Filter filter) {
        super(player, "Loot Filter", SlotUtils.fitSlots(filter.getFilters().size()));
        this.player = player;
        this.filter = filter;
    }

    @Override
    public void redraw() {
        int slot = 0;
        HashMap<ItemStack, ItemStack> itemsMap = new HashMap<>();
        for(ItemStack item : filter.getFilters().keySet()) {
            ItemStack newItem = item;
            ItemMeta meta = newItem.getItemMeta();
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            lore.add("");
            lore.add(filter.getFilters().get(item) ? CC.GreenB + "ENABLED" : CC.RedB + "DISABLED");
            meta.setLore(lore);
            newItem.setItemMeta(meta);
            itemsMap.put(item, newItem);
        }
        for(ItemStack item : filter.getFilters().keySet()) {
            this.setItem(slot++, new InventoryItem(itemsMap.get(item)).click(() -> {
                this.filter.toggle(item);
            }));
        }
    }
}
