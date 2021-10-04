package com.conaxgames.customskulls.util;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustomBlockUtil {

    public static CustomBlock getCustomItem(final ItemStack item) {
        NBTWrapper nbtItem = new NBTWrapper(item);
        if (nbtItem.hasKey("cosmicType")) {
            if (nbtItem.hasKey("cosmicType") && nbtItem.getString("cosmicType").equals("custom_block")) {
                NBTTagCompoundBuilder cosmicData = nbtItem.hasKey("cosmicData") ? new NBTTagCompoundBuilder(nbtItem.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
                NBTTagCompoundBuilder customBlockData = cosmicData.hasKey("customBlockData") ? new NBTTagCompoundBuilder(cosmicData.getNBTBase("customBlockData")) : new NBTTagCompoundBuilder();
                String abilityType = customBlockData.getString("type");
                if (CoreSkulls.customBlocks.containsKey(abilityType)) {
                    return CoreSkulls.customBlocks.get(abilityType);
                }
            }
        }
        return null;
    }

    public static Boolean isCustomItem(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("cosmicType") && nbtItem.getString("cosmicType").equals("custom_block");
    }

    public static List<AbilityData> getBlockDatasInChunk(Player player) {
        List<AbilityData> abilityDataList = new ArrayList<>();
        Chunk chunk = player.getLocation().getChunk();

        for (AbilityData data : AbilityData.getAbilityDataMap().values()) {
            if (data == null || data.getLocation() == null) continue;
            if (data.getAbilityType() == null) continue;
            if (chunk == null) continue;
            if (chunk == data.getLocation().getChunk()) { // The chunk the player is in has a CustomBlock in it!
                abilityDataList.add(data);
            }
        }
        return abilityDataList;
    }

    public static boolean chunkContainsABlock(Player player) {
        Chunk chunk = player.getLocation().getChunk();
        for (AbilityData data : AbilityData.getAbilityDataMap().values()) {
            if (data == null || data.getLocation() == null) continue;
            if (chunk == null) continue;
            if (chunk == data.getLocation().getChunk()) { // The chunk the player is in has a CustomBlock in it!
                return true;
            }
        }
        return false;
    }

    public static boolean chunkHasCertainBlock(Player p, String abilityData) {
        for (AbilityData abilityData2 : CustomBlockUtil.getBlockDatasInChunk(p)) {

            if (abilityData2 == null) {
                return false;
            }
            if (abilityData2.getAbilityType() == null) continue;

            CustomBlock customBlock = CoreSkulls.customBlocks.get(abilityData2.getAbilityType().getName());


            if (customBlock == null) {
                return false;
            }

            if (customBlock.getIdentifier().equals(abilityData)) {
                return true;
            }
        }
        return false;
    }

    public static List<AbilityData> getBlockDatasInChunk(Location l) {
        List<AbilityData> abilityDataList = new ArrayList<>();
        Chunk chunk = l.getChunk();
        for (AbilityData data : AbilityData.getAbilityDataMap().values()) {
            if (data == null) continue;
            if (data.getAbilityType() == null) continue;
            if (chunk == data.getLocation().getChunk()) { // The chunk the player is in has a CustomBlock in it!
                abilityDataList.add(data);
            }
        }
        return abilityDataList;
    }


    public static boolean chunkHasCertainBlock(Location l, String abilityData) {
        for (AbilityData abilityData2 : CustomBlockUtil.getBlockDatasInChunk(l)) {

            if (abilityData2 == null) {
                return false;
            }

            CustomBlock customBlock = CoreSkulls.customBlocks.get(abilityData2.getAbilityType().getName());
        if (CoreSkulls.customBlocks.get(abilityData2.getAbilityType().getName()) == null) continue;

            if (customBlock == null) {
                return false;
            }

            if (customBlock.getIdentifier().equals(abilityData)) {
                return true;
            }
        }
        return false;
    }
}
