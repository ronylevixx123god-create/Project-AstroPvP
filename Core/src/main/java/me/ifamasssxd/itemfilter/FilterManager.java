/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.craftbukkit.libs.com.google.gson.Gson
 *  org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder
 *  org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken
 *  org.bukkit.entity.Player
 *  org.bukkit.me.astropvp.cratekeys.inventory.Inventory
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.ifamasssxd.itemfilter;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.ifamasssxd.itemfilter.utils.ItemBuilder;
import me.ifamasssxd.itemfilter.utils.Utils;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FilterManager {
    ConcurrentHashMap<UUID, Filter> itemFilters = new ConcurrentHashMap();
    private static final Gson gson = new GsonBuilder().create();
    private static File jsonFile;
    ConcurrentHashMap<ItemCategory, List<ItemStack>> filterableItems = new ConcurrentHashMap();

    public ConcurrentHashMap<UUID, Filter> getItemFilters() {
        return this.itemFilters;
    }

    public Inventory getCategoryMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "Item Filter Categories");
        int index = 1;
        Filter filter = this.getItemFilters().get(player.getUniqueId());
        HashMap<ItemCategory, List<ItemStack>> filteredItemMap = null;
        if (filter != null) {
            filteredItemMap = filter.getItemMap();
        }
        for (ItemCategory category : ItemCategory.values()) {
            int amount = 1;
            ArrayList lore = Lists.newArrayList();
            if (filteredItemMap != null && filteredItemMap.get(category) != null) {
                amount = filteredItemMap.get(category).size();
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Filtered Items (" + ChatColor.WHITE + amount + ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + ")");
                for (ItemStack item : filteredItemMap.get(category)) {
                    lore.add(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "* " + ChatColor.WHITE + Utils.getItemName(item));
                }
            }
            inventory.setItem(index++, ItemBuilder.buildItem(category.getIconMaterial(), amount, (short)0, ChatColor.AQUA + ChatColor.BOLD.toString() + category.getItemName(), lore));
        }
        return inventory;
    }

    public ItemCategory getCategoryFromItem(ItemStack item, Material itemMaterial) {
        short durability = item.getDurability();
        for (Map.Entry<ItemCategory, List<ItemStack>> entries : this.filterableItems.entrySet()) {
            for (ItemStack filterableItem : entries.getValue()) {
                boolean countDura;
                boolean bl = countDura = itemMaterial != Material.MOB_SPAWNER && itemMaterial != Material.MONSTER_EGG && itemMaterial != Material.POTION;
                if (itemMaterial != filterableItem.getType() || countDura && filterableItem.getDurability() != durability) continue;
                return entries.getKey();
            }
        }
        return null;
    }

    public Inventory getFilterInventory(ItemCategory category, Player player) {
        Inventory inv = Bukkit.createInventory(null, Utils.getInventorySize(this.filterableItems.get(category).size()), "Item Filter: " + category.getItemName());
        Filter filter = this.itemFilters.get(player.getUniqueId());
        for (ItemStack item : this.filterableItems.get(category)) {
            inv.addItem(this.getFilterMenuItem(item, filter));
        }
        return inv;
    }

    public ItemStack getFilterMenuItem(ItemStack item, Filter filter) {
        ArrayList lore = Lists.newArrayList();
        boolean contains = filter != null && filter.canPickupItem(item, null);
        lore.add((contains ? ChatColor.GREEN + ChatColor.BOLD.toString() + "WILL" : ChatColor.RED + ChatColor.BOLD.toString() + "WILL NOT") + ChatColor.GRAY + " be picked up");
        lore.add(ChatColor.GRAY + "when item filter is " + ChatColor.GRAY + ChatColor.UNDERLINE.toString() + "enabled.");
        ChatColor color = contains ? ChatColor.GREEN : ChatColor.RED;
        item = ItemBuilder.buildItem(item.getType(), item.getDurability(), color + Utils.getItemName(item), lore);
        if (contains) {
            item = Utils.addGlow(item);
        }
        return item;
    }

    private void setCategoryItems(ItemCategory cat, List<ItemStack> items) {
        this.filterableItems.put(cat, items);
    }

    private void setCategoryMaterials(ItemCategory cat, List<Material> materials) {
        ArrayList items = Lists.newArrayList();
        for (Material mat : materials) {
            items.add(new ItemStack(mat, 1));
        }
        this.filterableItems.put(cat, items);
    }

    public void loadFilterItems() {
        this.setCategoryItems(ItemCategory.GEAR, Lists.newArrayList((new ItemStack(Material.IRON_HELMET)), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_SWORD), new ItemStack(Material.IRON_AXE), new ItemStack(Material.IRON_HOE), new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.IRON_SPADE), new ItemStack(Material.GOLD_HELMET), new ItemStack(Material.GOLD_CHESTPLATE), new ItemStack(Material.GOLD_LEGGINGS), new ItemStack(Material.GOLD_BOOTS), new ItemStack(Material.GOLD_SWORD), new ItemStack(Material.GOLD_AXE), new ItemStack(Material.GOLD_HOE), new ItemStack(Material.GOLD_PICKAXE), new ItemStack(Material.GOLD_SPADE), new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.DIAMOND_AXE), new ItemStack(Material.DIAMOND_HOE), new ItemStack(Material.DIAMOND_PICKAXE), new ItemStack(Material.DIAMOND_SPADE), new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.WOOD_SWORD), new ItemStack(Material.WOOD_AXE), new ItemStack(Material.WOOD_HOE), new ItemStack(Material.WOOD_PICKAXE), new ItemStack(Material.WOOD_SPADE), new ItemStack(Material.BOW), new ItemStack(Material.ARROW), new ItemStack(Material.ENDER_PEARL), new ItemStack(Material.POTION), new ItemStack(Material.GOLDEN_APPLE), new ItemStack(Material.EYE_OF_ENDER), new ItemStack(Material.SKULL_ITEM, 1, (short) 3)));
        this.setCategoryItems(ItemCategory.REDSTONE, Lists.newArrayList(new ItemStack(Material.REDSTONE), new ItemStack(Material.REDSTONE_BLOCK), new ItemStack(Material.REDSTONE_LAMP_OFF), new ItemStack(Material.REDSTONE_TORCH_ON), new ItemStack(Material.REDSTONE_ORE), new ItemStack(Material.REDSTONE_COMPARATOR), new ItemStack(Material.DIODE), new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.TNT), new ItemStack(Material.WEB), new ItemStack(Material.SPONGE), new ItemStack(Material.ICE), new ItemStack(Material.PACKED_ICE), new ItemStack(Material.DISPENSER), new ItemStack(Material.DROPPER), new ItemStack(Material.MONSTER_EGG, 1, (short) 50), new ItemStack(Material.PISTON_BASE), new ItemStack(Material.PISTON_STICKY_BASE), new ItemStack(Material.HOPPER), new ItemStack(Material.FLINT_AND_STEEL)));
        this.setCategoryMaterials(ItemCategory.POTION, Lists.newArrayList(Material.getMaterial(375), Material.getMaterial(378), Material.getMaterial(370), Material.getMaterial(341), Material.getMaterial(372), Material.getMaterial(382), Material.getMaterial(353), Material.getMaterial(348), Material.getMaterial(89), Material.getMaterial(396), Material.getMaterial(117), Material.getMaterial(373), Material.getMaterial(374), Material.getMaterial(111), Material.getMaterial(391), Material.getMaterial(406), Material.getMaterial(40), Material.getMaterial(260), Material.getMaterial(39), Material.getMaterial(351), Material.getMaterial(394), Material.getMaterial(322)));
        this.setCategoryMaterials(ItemCategory.FOOD, Lists.newArrayList(Material.getMaterial(352), Material.getMaterial(391), Material.getMaterial(86), Material.getMaterial(103), Material.getMaterial(360), Material.getMaterial(363), Material.getMaterial(364), Material.getMaterial(338), Material.getMaterial(81), Material.getMaterial(319), Material.getMaterial(320), Material.getMaterial(322), Material.getMaterial(361), Material.getMaterial(362), Material.getMaterial(295), Material.getMaterial(392), Material.getMaterial(393), Material.getMaterial(394)));
        this.setCategoryMaterials(ItemCategory.VALUABLES, Lists.newArrayList(Material.MOB_SPAWNER, Material.getMaterial(383), Material.getMaterial(421), Material.getMaterial(54), Material.getMaterial(146), Material.getMaterial(130), Material.getMaterial(339), Material.getMaterial(395), Material.getMaterial(384), Material.getMaterial(76), Material.getMaterial(340), Material.getMaterial(351), Material.getMaterial(50), Material.getMaterial(388), Material.getMaterial(378), Material.getMaterial(322), Material.getMaterial(264), Material.BONE, Material.ENDER_PEARL, Material.BLAZE_ROD, Material.NETHER_STAR, Material.FIREWORK_CHARGE, Material.FIREBALL, Material.BEACON, Material.ENDER_PORTAL_FRAME, Material.EYE_OF_ENDER, Material.LEASH));
        this.setCategoryItems(ItemCategory.ORES, Lists.newArrayList(new ItemStack(Material.getMaterial(15)), new ItemStack(Material.getMaterial(265)), new ItemStack(Material.getMaterial(42)), new ItemStack(Material.getMaterial(14)), new ItemStack(Material.getMaterial(266)), new ItemStack(Material.getMaterial(41)), new ItemStack(Material.getMaterial(73)), new ItemStack(Material.getMaterial(331)), new ItemStack(Material.getMaterial(152)), new ItemStack(Material.getMaterial(21)), new ItemStack(Material.getMaterial(351), 1, (short) 4), new ItemStack(Material.getMaterial(22)), new ItemStack(Material.getMaterial(16)), new ItemStack(Material.getMaterial(263)), new ItemStack(Material.getMaterial(173)), new ItemStack(Material.getMaterial(129)), new ItemStack(Material.getMaterial(388)), new ItemStack(Material.getMaterial(133)), new ItemStack(Material.getMaterial(56)), new ItemStack(Material.getMaterial(264)), new ItemStack(Material.getMaterial(57))));
        this.setCategoryMaterials(ItemCategory.OTHERS, Lists.newArrayList(Material.SAND, Material.GRAVEL, Material.GREEN_RECORD, Material.DRAGON_EGG));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadFilters() {
        this.loadFilterItems();
        long start = System.currentTimeMillis();
        try {
            if (!AstroCore.getInstance().getDataFolder().exists()) {
                AstroCore.getInstance().getDataFolder().mkdir();
            }
            if (!(jsonFile = new File(AstroCore.getInstance().getDataFolder(), "playerFilters.json")).exists()) {
                jsonFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            try {
                String line;
                StringBuilder build = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    build.append(line);
                }
                this.itemFilters = gson.fromJson(build.toString(), new TypeToken<ConcurrentHashMap<UUID, Filter>>(){}.getType());
                if (this.itemFilters == null) {
                    this.itemFilters = new ConcurrentHashMap();
                }
                System.out.println("Took " + (System.currentTimeMillis() - start) + "ms to load " + this.itemFilters.size() + " player filters.");
            }
            finally {
                if (Collections.singletonList(reader).get(0) != null) {
                    reader.close();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFilters() {
        try {
            String map = gson.toJson(this.itemFilters);
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
}

