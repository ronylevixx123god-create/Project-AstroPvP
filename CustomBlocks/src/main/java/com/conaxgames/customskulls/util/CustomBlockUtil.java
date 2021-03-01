package com.conaxgames.customskulls.util;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.blocks.CustomBlock;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class CustomBlockUtil {

    public static CustomBlock getCustomItem(final ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.hasNBTData()) {
            if (nbtItem.hasKey("conaxCustomBlock")) {
                String abilityType = nbtItem.getString("conaxCustomBlock");
                if (CoreSkulls.customBlocks.containsKey(abilityType)) {
                    return CoreSkulls.customBlocks.get(abilityType);
                }
            }
        }
        return null;
    }

    public static Boolean isCustomItem(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("conaxCustomBlock");
    }
}
