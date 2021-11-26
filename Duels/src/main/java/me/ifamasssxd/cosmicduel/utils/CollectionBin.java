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
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
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
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CollectionBin implements Listener {
    public static void loadCollectionBins() {
        collectionBinFile = new File(CosmicDuel.get().getDataFolder(), "collectionbins.yml");
        if (!collectionBinFile.exists())
            try {
                collectionBinFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        config = YamlConfiguration.loadConfiguration(collectionBinFile);
        if (config.contains("Bins"))
            for (String uuid : config.getConfigurationSection("Bins").getKeys(false)) {
                List<ItemStack> items = ItemSerialization.itemListFromBase64(config.getString("Bins." + uuid));
                if (items.isEmpty())
                    continue;
                collectionBin.put(uuid, items);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addItem(Player p, ItemStack is) {
        List<ItemStack> itemList = new ArrayList<>();
        if (collectionBin.containsKey(p.getUniqueId().toString()))
            itemList = collectionBin.get(p.getUniqueId().toString());
        itemList.add(is);
        collectionBin.put(p.getUniqueId().toString(), itemList);
    }

    public static void addItems(Player p, List<ItemStack> items) {
        List<ItemStack> itemList = new ArrayList<>();
        if (collectionBin.containsKey(p.getUniqueId().toString()))
            itemList = collectionBin.get(p.getUniqueId().toString());
        itemList.addAll(items);
        collectionBin.put(p.getUniqueId().toString(), itemList);
    }

    public static List<ItemStack> getCollectionItems(Player p) {
        if (collectionBin.containsKey(p.getUniqueId().toString()))
            return collectionBin.get(p.getUniqueId().toString());
        return Lists.newArrayList();
    }

    public static void showCollectionBin(Player p) {
        List<ItemStack> items = getCollectionItems(p);
        if (items.size() <= 0) {
            p.sendMessage(ChatColor.RED + "You do not have any items in your stake collection bin.");
            return;
        }
        Inventory inv = Bukkit.createInventory((InventoryHolder)null, PlayerUtils.getRoundedSize(items.size()), INVENTORY_NAME);
        for (ItemStack is : items) {
            inv.addItem(new ItemStack[] { is });
        }
        p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 1.4F);
        p.openInventory(inv);
    }

    public static void removeItem(Player p, ItemStack is) {
        List<ItemStack> itemList = new ArrayList<>();
        if (collectionBin.containsKey(p.getUniqueId().toString()))
            itemList = collectionBin.get(p.getUniqueId().toString());
        if (itemList.contains(is)) {
            itemList.remove(is);
            collectionBin.put(p.getUniqueId().toString(), itemList);
        }
    }

    public static void removePotions(Player p) {
        List<ItemStack> itemList = new ArrayList<>();
        if (collectionBin.containsKey(p.getUniqueId().toString()))
            itemList = collectionBin.get(p.getUniqueId().toString());
        List<ItemStack> collectionBinItems = new ArrayList<>();
        collectionBinItems.addAll(itemList);
        for (ItemStack item : collectionBinItems) {
            if (item.getType().equals(Material.POTION))
                removeItem(p, item);
        }
    }

    public static HashMap<String, List<ItemStack>> getCollectionBin() {
        return collectionBin;
    }

    @EventHandler
    public void handleClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equalsIgnoreCase(INVENTORY_NAME)) {
            e.setCancelled(true);
            if (e.getRawSlot() >= e.getInventory().getSize())
                return;
            Player p = (Player)e.getWhoClicked();
            ItemStack is = e.getCurrentItem();
            if (p.getInventory().firstEmpty() == -1)
                return;
            removeItem(p, is);
            p.getInventory().addItem(new ItemStack[] { is });
            e.getInventory().removeItem(new ItemStack[] { is });
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.9F);
            p.updateInventory();
        }
    }

    @EventHandler
    public void onPlayerInventory(InventoryCloseEvent e) {
        if (e.getInventory().getName().equalsIgnoreCase(INVENTORY_NAME)) {
            List<ItemStack> item = new ArrayList<>();
            for (ListIterator<ItemStack> listIterator = e.getInventory().iterator(); listIterator.hasNext(); ) {
                ItemStack is = listIterator.next();
                if (is == null ||
                        is.getType() == Material.AIR)
                    continue;
                item.add(is);
            }
            if (item.isEmpty() && collectionBin.containsKey(e.getPlayer().getUniqueId().toString()))
                collectionBin.remove(e.getPlayer().getUniqueId().toString());
            collectionBin.put(e.getPlayer().getUniqueId().toString(), item);
        }
    }

    private static HashMap<String, List<ItemStack>> collectionBin = new HashMap<>();

    private static String INVENTORY_NAME = "Collection Bin";

    private static File collectionBinFile;

    private static YamlConfiguration config;

    static boolean saving = true;
}
