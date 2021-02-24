package com.conaxgames.customskulls.util;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.blocks.CustomBlock;
import org.bukkit.inventory.ItemStack;

public class CustomBlockUtil {

    public static CustomBlock getCustomBlock(final ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        return CoreSkulls.customBlocks.get(wrapper.getCompound("conaxCustomBlock").getString("conaxCustomBlockName"));
    }

    public static Boolean isCustomBlock(ItemStack itemStack) {
        NBTWrapper wrapper = new NBTWrapper(itemStack);
        return (wrapper.hasKey("conaxCustomBlock"));
    }
}
