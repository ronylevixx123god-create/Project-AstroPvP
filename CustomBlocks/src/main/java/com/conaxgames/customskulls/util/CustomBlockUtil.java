package com.conaxgames.customskulls.util;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.InventoryUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public static Map<String, AbilityData> getCustomBlocksInWorld() {
        return AbilityData.getAbilityDataMap();
    }


    public static List<AbilityData> getBlockDatasInChunk(Player player) {
        List<AbilityData> abilityDataList = new ArrayList<>();
        Chunk chunk = player.getLocation().getChunk();
        for (AbilityData data : AbilityData.getAbilityDataMap().values()) {
            if (chunk == data.getLocation().getChunk()) { // The chunk the player is in has a CustomBlock in it!
                abilityDataList.add(data);
            }
        }
        return abilityDataList;
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

    public static Location getClosestChestLocation(Location location) {
        List<Block> nearbyChests = getNearbyChests(location.getBlock(), 2);

        for (Block block : nearbyChests) {
            Chest chest = (Chest) block.getLocation().getBlock().getState();
            if (isInvEmpty(chest.getInventory())) {
                return block.getLocation();
            }
        }
        return null;

    }

    public static List<Block> getNearbyChests(Block start, int radius){
        if (radius < 0) {
            return new ArrayList<Block>(0);
        }
        int iterations = (radius * 2) + 1;
        List<Block> blocks = new ArrayList<Block>(iterations * iterations * iterations);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (start.getRelative(x,y,z).getType() == Material.CHEST) {
                        blocks.add(start.getRelative(x, y, z));
                    }
                }
            }
        }
        return blocks;
    }

    public static boolean isInvEmpty(Inventory inv) {
        for (ItemStack item : inv.getContents()) {
            if (item != null) {
                return false;
            }
        }
        return true;
    }


    public static boolean chunkHasCertainBlock(Player p, String abilityData) {
        for (AbilityData abilityData2 : CustomBlockUtil.getBlockDatasInChunk(p)) {

            if (abilityData2 == null) {
                return false;
            }

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