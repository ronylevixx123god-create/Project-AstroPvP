package com.conaxgames.customskulls.util;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
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

    public static AbilityData getBlockDataInChunk(Player player) {
        Chunk chunk = player.getLocation().getChunk();
        for (AbilityData data : AbilityData.getAbilityDataMap().values()) {
            if (chunk == data.getLocation().getChunk()) { // The chunk the player is in has a CustomBlock in it!
                return data;
            }
        }
        return null;
    }

    public static Boolean chunkContainsABlock(Player player) {
        Chunk chunk = player.getLocation().getChunk();
        for (AbilityData data : AbilityData.getAbilityDataMap().values()) {
            if (chunk == data.getLocation().getChunk()) { // The chunk the player is in has a CustomBlock in it!
                return true;
            }
        }
        return false;
    }
}
