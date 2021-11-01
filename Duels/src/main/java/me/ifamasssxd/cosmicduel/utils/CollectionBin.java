/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryCloseEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.Inventory
 *  org.bukkit.me.astropvp.cratekeys.inventory.InventoryHolder
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.ifamasssxd.cosmicduel.utils;

import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionBin
implements Listener {
    private static final HashMap<String, List<ItemStack>> collectionBin = new HashMap();
    private static final String INVENTORY_NAME = "Collection Bin";
    private static File collectionBinFile;
    private static YamlConfiguration config;
    static boolean saving;

    public static void loadCollectionBins() {
        collectionBinFile = new File(CosmicDuel.get().getDataFolder(), "collectionbins.yml");
        if (!collectionBinFile.exists()) {
            try {
                collectionBinFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if ((config = YamlConfiguration.loadConfiguration(collectionBinFile)).contains("Bins")) {
            for (String uuid : config.getConfigurationSection("Bins").getKeys(false)) {
                List<ItemStack> items = ItemSerialization.itemListFromBase64(config.getString("Bins." + uuid));
                if (items.isEmpty()) continue;
                collectionBin.put(uuid, items);
            }
        }
    }

    public static void save() {
        try {
            for (Map.Entry<String, List<ItemStack>> entry : collectionBin.entrySet()) {
                String uuid = entry.getKey();
                List<ItemStack> items = entry.getValue();
                String serialized = ItemSerialization.itemListToBase64(items);
                config.set("Bins." + uuid, serialized);
            }
            config.save(collectionBinFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addItem(Player p, ItemStack is) {
        List<ItemStack> itemList = new ArrayList<ItemStack>();
        if (collectionBin.containsKey(p.getUniqueId().toString())) {
            itemList = collectionBin.get(p.getUniqueId().toString());
        }
        itemList.add(is);
        collectionBin.put(p.getUniqueId().toString(), itemList);
    }

    public static void addItems(Player p, List<ItemStack> items) {
        List<ItemStack> itemList = new ArrayList<ItemStack>();
        if (collectionBin.containsKey(p.getUniqueId().toString())) {
            itemList = collectionBin.get(p.getUniqueId().toString());
        }
        itemList.addAll(items);
        collectionBin.put(p.getUniqueId().toString(), itemList);
    }

    public static List<ItemStack> getCollectionItems(Player p) {
        if (collectionBin.containsKey(p.getUniqueId().toString())) {
            return collectionBin.get(p.getUniqueId().toString());
        }
        return Lists.newArrayList();
    }

    public static void showCollectionBin(Player p) {
        List<ItemStack> items = CollectionBin.getCollectionItems(p);
        if (items.size() <= 0) {
            p.sendMessage(ChatColor.RED + "You do not have any me.astropvp.cratekeys.items in your stake collection bin.");
            return;
        }
        Inventory inv = Bukkit.createInventory(null, 54, INVENTORY_NAME);
        for (ItemStack is : items) {
            inv.addItem(is);
        }
        p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 1.4f);
        p.openInventory(inv);
    }

    public static void removeItem(Player p, ItemStack is) {
        List<ItemStack> itemList = new ArrayList();
        if (collectionBin.containsKey(p.getUniqueId().toString())) {
            itemList = collectionBin.get(p.getUniqueId().toString());
        }
        if (itemList.contains(is)) {
            itemList.remove(is);
            collectionBin.put(p.getUniqueId().toString(), itemList);
        }
    }

    @EventHandler
    public void handleClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equalsIgnoreCase(INVENTORY_NAME)) {
            e.setCancelled(true);
            if (e.getRawSlot() >= e.getInventory().getSize()) {
                return;
            }
            Player p = (Player)e.getWhoClicked();
            ItemStack is = e.getCurrentItem();
            if (p.getInventory().firstEmpty() == -1) {
                return;
            }
            CollectionBin.removeItem(p, is);
            p.getInventory().addItem(is);
            e.getInventory().removeItem(is);
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.9f);
            p.updateInventory();
        }
    }

    @EventHandler
    public void onPlayerInventory(InventoryCloseEvent e) {
        if (e.getInventory().getName().equalsIgnoreCase(INVENTORY_NAME)) {
            ArrayList<ItemStack> item = new ArrayList<ItemStack>();
            for (ItemStack is : e.getInventory()) {
                if (is == null || is.getType() == Material.AIR) continue;
                item.add(is);
            }
            if (item.isEmpty()) {
                collectionBin.remove(e.getPlayer().getUniqueId().toString());
            }
            collectionBin.put(e.getPlayer().getUniqueId().toString(), item);
        }
    }

    static {
        saving = true;
    }
}

