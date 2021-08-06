
package me.astropvp.itemflip.inventory;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import me.astropvp.itemflip.data.ItemFlipData;
import me.astropvp.itemflip.data.ItemFlipPlayerData;
import me.astropvp.itemflip.enums.Color;
import me.astropvp.itemflip.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemFlipActiveInventory {
    public static Inventory getInventory(ItemFlipData data) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 9, CC.Gray + "Active Item Flip");
        inventory.setItem(0, getHead(((ItemFlipPlayerData)data.getData().get(0)).getColor(), ((ItemFlipPlayerData)data.getData().get(0)).getPlayer()));
        inventory.setItem(8, getHead(((ItemFlipPlayerData)data.getData().get(1)).getColor(), ((ItemFlipPlayerData)data.getData().get(1)).getPlayer()));
        inventory.setItem(1, getPane(((ItemFlipPlayerData)data.getData().get(0)).getColor(), ((ItemFlipPlayerData)data.getData().get(0)).getPlayer()));
        inventory.setItem(7, getPane(((ItemFlipPlayerData)data.getData().get(1)).getColor(), ((ItemFlipPlayerData)data.getData().get(1)).getPlayer()));
        if (data.getActiveColor() != null) {
            inventory.setItem(4, getActiveFlipPane(data.getActiveColor()));
        }

        return inventory;
    }

    private static ItemStack getHead(Color color, Player taker) {
        ItemStack item = (new ItemBuilder(Material.SKULL_ITEM)).damage((short)3).displayname(CC.WhiteB + taker.getName()).lore(CC.Green + "Balance: $" + NumberUtils.formatMoney(EconomyUtils.getBalance(taker))).build();
        SkullMeta meta = (SkullMeta)item.getItemMeta();
        String name = taker.getName();
        meta.setOwner(name);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getPane(Color color, Player taker) {
        ItemStack item = (new ItemBuilder(Material.STAINED_GLASS_PANE)).damage(color.getId()).displayname(color.getPrefix() + CC.Bold + color.getName()).damage(color.getId()).lore(CC.Gray + taker.getName() + "'s Color").build();
        return item;
    }

    private static ItemStack getActiveFlipPane(Color color) {
        return (new ItemBuilder(Material.STAINED_GLASS)).damage(color.getId()).displayname(color.getPrefix() + CC.Bold + color.getName()).damage(color.getId()).build();
    }
}
