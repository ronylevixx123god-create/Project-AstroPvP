package net.astropvp.inventories;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.CollectPlugin;
import net.astropvp.struct.CollectCategory;
import net.astropvp.struct.PlayerCollect;
import net.astropvp.utils.SlotUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CollectInventory extends CosmicGUI {
    public CollectInventory(Player player) {
        super(player, player.getName() + "'s Collect", SlotUtils.fitSlots(CollectPlugin.getInstance().getCollectManager().getPlayerCollect(player).getCategories().size()));
    }

    public void redraw() {
        PlayerCollect collect = CollectPlugin.getInstance().getCollectManager().getPlayerCollect(player);
        List<CollectCategory> categories = collect.getCategories();
        int i = 0;
        for(CollectCategory cat : categories) {
            setItem(i, getCategoryItem(player, cat));
            i++;
        }
    }

    private InventoryItem getCategoryItem(Player player, CollectCategory cat) {
        ItemStack item = new ItemStack(cat.getType().getMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(cat.getName());
        List<String> lore = new ArrayList<>();
        lore.add(CC.Gray + "Click To View");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return new InventoryItem(item).click(() -> {
            player.closeInventory();
            player.openInventory(cat.getInventory());
        });
    }

    private InventoryItem getFillerItem() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.Gray);
        item.setItemMeta(meta);
        return new InventoryItem(item);
    }
}
