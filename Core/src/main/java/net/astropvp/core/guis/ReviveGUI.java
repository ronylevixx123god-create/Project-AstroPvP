package net.astropvp.core.guis;

import java.util.UUID;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.data.InventoryData;
import net.astropvp.core.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ReviveGUI {
    public static Inventory getInventory(Player player, int page) {
        UUID uuid = player.getUniqueId();
        if (!InventoryData.getData().containsKey(uuid))
            return null;
        Inventory inventory = Bukkit.createInventory(null, 54, String.valueOf(CC.DarkGray) + "Revive GUI");
        InventoryData[] data = (InventoryData[])InventoryData.getData().get(uuid);
        if (data[page] == null)
            return null;
        InventoryData x = data[page];
        ItemStack[] contents = x.getContents();
        ItemStack[] armorContents = x.getArmorContents();
        int i;
        for (i = 0; i < contents.length; i++)
            inventory.setItem(i, contents[i]);
        for (i = 0; i < 8; i += 2)
            inventory.setItem(i + 46, armorContents[i / 2]);
        if (page - 1 >= 0 && data[page - 1] != null)
            inventory.setItem(45, ItemUtils.getPreviousPageItem(page - 1, player.getName()));
        if (page + 1 < data.length && data[page + 1] != null)
            inventory.setItem(53, ItemUtils.getNextPageItem(page + 1, player.getName()));
        inventory.setItem(49, ItemUtils.getRevivePlayerItem(page, player.getName()));
        return inventory;
    }
}
