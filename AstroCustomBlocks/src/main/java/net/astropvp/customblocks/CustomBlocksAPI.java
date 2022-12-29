package net.astropvp.customblocks;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.LocationUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import de.tr7zw.nbtapi.NBTItem;
import net.astropvp.customblocks.data.CustomBlockData;
import net.astropvp.customblocks.data.CustomBlockManager;
import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class CustomBlocksAPI {
    private static CustomBlockManager customBlockManager = CustomBlocks.getInstance().getCustomBlockManager();
    public static boolean isCustomBlockItem(ItemStack itemStack) {
        if (ItemUtils.isAirOrNull(itemStack)) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("cosmicType") && nbtItem.getString("cosmicType").equals("custom_block");
    }

    public static CustomBlock getCustomBlockFromItem(ItemStack itemStack) {
        if (isCustomBlockItem(itemStack)) {
            NBTWrapper nbtItem = new NBTWrapper(itemStack);
            NBTTagCompoundBuilder cosmicData = nbtItem.hasKey("cosmicData") ? new NBTTagCompoundBuilder(nbtItem.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
            NBTTagCompoundBuilder customBlockData = cosmicData.hasKey("customBlockData") ? new NBTTagCompoundBuilder(cosmicData.getNBTBase("customBlockData")) : new NBTTagCompoundBuilder();
            CustomBlockType type = CustomBlockType.valueOf(customBlockData.getString("type"));
            int level = customBlockData.getInt("level");
            return new CustomBlock(level, type);
        }
        return null;
    }

    public static CustomBlockData getCustomBlockData(Location location) {
        if (isCustomBlock(location)) {
            return customBlockManager.getCustomBlockData(location);
        }
        return null;
    }

    public static boolean isCustomBlock(Location location) {
        return customBlockManager.contains(LocationUtils.getStringFromLocation(location));
    }

}
