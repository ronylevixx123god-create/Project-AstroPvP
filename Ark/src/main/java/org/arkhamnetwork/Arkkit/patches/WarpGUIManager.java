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
    private ConcurrentHashMap<String, CustomWarps> customWarps = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, CustomWarps> getCustomWarps() {
        return this.customWarps;
    }

    private static Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

    private static File jsonFile;

    public void loadHomes() {
        System.out.println("Loading faction warps..");
        long start = System.currentTimeMillis();
        try {
            jsonFile = new File(Arkkit.get().getDataFolder(), "customFWarps.json");
            if (!jsonFile.exists())
                jsonFile.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            try {
                StringBuilder build = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    build.append(line);
                this.customWarps = (ConcurrentHashMap<String, CustomWarps>)gson.fromJson(build.toString(), (new TypeToken<ConcurrentHashMap<String, CustomWarps>>() {

                }).getType());
                if (this.customWarps == null)
                    this.customWarps = new ConcurrentHashMap<>();
                System.out.println("Took " + (System.currentTimeMillis() - start) + "ms to load " + this.customWarps.size() + " unique players custom homes.");
            } finally {
                if (Collections.<BufferedReader>singletonList(reader).get(0) != null)
                    reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Material> materials = Lists.newArrayList(new Material[] {
            Material.DIRT, Material.STONE, Material.COBBLESTONE, Material.LOG, Material.LOG_2, Material.WORKBENCH, Material.PISTON_BASE, Material.NOTE_BLOCK, Material.MOB_SPAWNER, Material.BOOKSHELF,
            Material.TNT, Material.STEP, Material.ANVIL, Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK, Material.NETHER_BRICK, Material.NETHERRACK, Material.ENDER_STONE, Material.CHEST,
            Material.ENDER_CHEST, Material.SMOOTH_BRICK, Material.SNOW_BLOCK, Material.SOUL_SAND, Material.QUARTZ_BLOCK, Material.DIAMOND_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_HOE,
            Material.ICE, Material.PACKED_ICE, Material.DISPENSER, Material.FURNACE, Material.HOPPER, Material.GLASS, Material.SAND, Material.SPONGE, Material.OBSIDIAN, Material.BEDROCK,
            Material.ENCHANTMENT_TABLE, Material.CACTUS, Material.GLOWSTONE, Material.DIAMOND, Material.EMERALD, Material.GOLD_INGOT, Material.IRON_INGOT, Material.REDSTONE, Material.BUCKET, Material.LAVA_BUCKET,
            Material.WATER_BUCKET, Material.GOLDEN_APPLE, Material.EXP_BOTTLE, Material.BED });

    public List<Material> getMaterials() {
        return this.materials;
    }

    public Inventory getMaterialSelection() {
        Inventory inventory = Bukkit.createInventory(null, 54, "Select Warp Icon");
        for (Material material : getMaterials()) {
            inventory.addItem(new ItemStack[] { ItemUtils.buildItem(material, (short)0, null, new String[] { ChatColor.GRAY + "Click to select material." }) });
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
            } finally {
                if (Collections.<FileWriter>singletonList(writer).get(0) != null)
                    writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DecimalFormat format = new DecimalFormat("#.#");

    public Inventory getWarpInventory(Player player) {
        FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
        Faction faction = fplayer.getFaction();
        List<Warp> warps = CmdWarp.getWarps(faction);
        Inventory inventory = Bukkit.createInventory(null, InventoryUtils.fitInventorySize(warps.size()), "Faction Warps");
        CustomWarps customWarp = getCustomWarps().get(faction.getId());
        for (Warp warp : warps) {
            try {
                Material material = Material.GRASS;
                if (customWarp != null && customWarp.isCustomWarp(warp.getName()))
                    material = customWarp.get(warp.getName());
                ItemStack item = ItemUtils.buildItem(material, (short)0, ChatColor.AQUA + ChatColor.BOLD.toString() + warp.getName().replace("_", " "), new String[] { ChatColor.GRAY + "World: " + ChatColor.WHITE + warp
                        .getWorldName(), "", ChatColor.GRAY + "Left-Click to teleport", ChatColor.GRAY + "Right-Click to edit icon",

                        warp.getPassword().equals("nullvalue") ? null : "",
                        !warp.getPassword().equals("nullvalue") ? (ChatColor.RED + ChatColor.BOLD.toString() + "PASSWORD PROTECTED") : "" });
                inventory.addItem(new ItemStack[] { item });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (getCustomWarps().containsKey(faction.getId())) {
            CustomWarps warp = getCustomWarps().get(faction.getId());
            for (String warpName : warp.keySet()) {
                try {
                    if (CmdWarp.getWarp(faction, warpName) == null)
                        warp.remove(warpName);
                } catch (Exception e) {
                    warp.remove(warpName);
                }
            }
            if (warp.isEmpty())
                getCustomWarps().remove(faction.getId());
        }
        return inventory;
    }
}
