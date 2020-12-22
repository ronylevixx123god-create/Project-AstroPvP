/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  markehme.factionsplus.Cmds.CmdWarp
 *  markehme.factionsplus.extras.Warp
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.craftbukkit.libs.com.google.gson.Gson
 *  org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder
 *  org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import markehme.factionsplus.Cmds.CmdWarp;
import markehme.factionsplus.extras.Warp;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.utils.InventoryUtils;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

class WarpGUIManager {
    private ConcurrentHashMap<String, CustomWarps> customWarps = new ConcurrentHashMap();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static File jsonFile;
    private List<Material> materials = Lists.newArrayList(Material.DIRT, Material.STONE, Material.COBBLESTONE, Material.LOG, Material.LOG_2, Material.WORKBENCH, Material.PISTON_BASE, Material.NOTE_BLOCK, Material.MOB_SPAWNER, Material.BOOKSHELF, Material.TNT, Material.STEP, Material.ANVIL, Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK, Material.NETHER_BRICK, Material.NETHERRACK, Material.ENDER_STONE, Material.CHEST, Material.ENDER_CHEST, Material.SMOOTH_BRICK, Material.SNOW_BLOCK, Material.SOUL_SAND, Material.QUARTZ_BLOCK, Material.DIAMOND_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_HOE, Material.ICE, Material.PACKED_ICE, Material.DISPENSER, Material.FURNACE, Material.HOPPER, Material.GLASS, Material.SAND, Material.SPONGE, Material.OBSIDIAN, Material.BEDROCK, Material.ENCHANTMENT_TABLE, Material.CACTUS, Material.GLOWSTONE, Material.DIAMOND, Material.EMERALD, Material.GOLD_INGOT, Material.IRON_INGOT, Material.REDSTONE, Material.BUCKET, Material.LAVA_BUCKET, Material.WATER_BUCKET, Material.GOLDEN_APPLE, Material.EXP_BOTTLE, Material.BED);
    private DecimalFormat format = new DecimalFormat("#.#");

    WarpGUIManager() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadHomes() {
        System.out.println("Loading faction warps..");
        long start = System.currentTimeMillis();
        try {
            jsonFile = new File(Arkkit.get().getDataFolder(), "customFWarps.json");
            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            try {
                String line;
                StringBuilder build = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    build.append(line);
                }
                this.customWarps = (ConcurrentHashMap)gson.fromJson(build.toString(), new TypeToken<ConcurrentHashMap<String, CustomWarps>>(){}.getType());
                if (this.customWarps == null) {
                    this.customWarps = new ConcurrentHashMap();
                }
                System.out.println("Took " + (System.currentTimeMillis() - start) + "ms to load " + this.customWarps.size() + " unique players custom homes.");
            }
            finally {
                if (Collections.singletonList(reader).get(0) != null) {
                    reader.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Material> getMaterials() {
        return this.materials;
    }

    public Inventory getMaterialSelection() {
        Inventory inventory = Bukkit.createInventory(null, (int)54, (String)"Select Warp Icon");
        for (Material material : this.getMaterials()) {
            inventory.addItem(new ItemStack[]{ItemUtils.buildItem(material, (short)0, null, (Object)ChatColor.GRAY + "Click to select material.")});
        }
        return inventory;
    }

    public void saveWarps() {
        try {
            String map = gson.toJson(this.customWarps);
            FileWriter writer = new FileWriter(jsonFile);
            try {
                writer.write(map);
                writer.flush();
            }
            finally {
                if (Collections.singletonList(writer).get(0) != null) {
                    writer.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Inventory getWarpInventory(Player player) {
        FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
        Faction faction = fplayer.getFaction();
        List<Warp> warps = CmdWarp.getWarps((Faction)faction);
        Inventory inventory = Bukkit.createInventory(null, (int)InventoryUtils.fitInventorySize(warps.size()), (String)"Faction Warps");
        CustomWarps customWarp = this.getCustomWarps().get(faction.getId());
        for (Warp warp : warps) {
            try {
                Material material = Material.GRASS;
                if (customWarp != null && customWarp.isCustomWarp(warp.getName())) {
                    material = (Material)customWarp.get(warp.getName());
                }
                ItemStack item = ItemUtils.buildItem(material, (short)0, (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + warp.getName().replace("_", " "), (Object)ChatColor.GRAY + "World: " + (Object)ChatColor.WHITE + warp.getWorldName(), "", (Object)ChatColor.GRAY + "Left-Click to teleport", (Object)ChatColor.GRAY + "Right-Click to edit icon", warp.getPassword().equals("nullvalue") ? null : "", !warp.getPassword().equals("nullvalue") ? (Object)ChatColor.RED + ChatColor.BOLD.toString() + "PASSWORD PROTECTED" : "");
                inventory.addItem(new ItemStack[]{item});
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.getCustomWarps().containsKey(faction.getId())) {
            CustomWarps warp = this.getCustomWarps().get(faction.getId());
            for (String warpName : warp.keySet()) {
                try {
                    if (CmdWarp.getWarp((Faction)faction, (String)warpName) != null) continue;
                    warp.remove(warpName);
                }
                catch (Exception e) {
                    warp.remove(warpName);
                }
            }
            if (warp.isEmpty()) {
                this.getCustomWarps().remove(faction.getId());
            }
        }
        return inventory;
    }

    public ConcurrentHashMap<String, CustomWarps> getCustomWarps() {
        return this.customWarps;
    }
}

