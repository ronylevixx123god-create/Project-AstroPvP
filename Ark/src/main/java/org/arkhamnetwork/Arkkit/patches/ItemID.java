/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.GeneralUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CommandItemID;
import org.arkhamnetwork.Arkkit.patches.ItemIDAutoSaveTask;
import org.arkhamnetwork.Arkkit.patches.ItemIDListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class ItemID
extends ArkPlugin {
    public static volatile HashSet<Long> blacklist_iid;
    public static long next_iid;
    protected static List<Material> otherItemsToMonitor;
    protected static HashMap<Material, int[]> durabilityMap;

    public ItemID(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        next_iid = Arkkit.get().getConfig().getLong("settings.itemID.next_iid");
        blacklist_iid = new HashSet();
        Iterator<Long> iterator = Arkkit.get().getConfig().getLongList("settings.itemID.blacklist_iid").iterator();
        while (iterator.hasNext()) {
            long l = (Long)iterator.next();
            blacklist_iid.add(l);
        }
        for (Material m : otherItemsToMonitor) {
            if (m == Material.INK_SACK) {
                durabilityMap.put(m, new int[]{0, 1});
            }
            if (m != Material.getMaterial((int)383)) continue;
            durabilityMap.put(m, new int[]{0, 51, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 90, 91, 92, 93, 94, 95, 96, 97, 99});
        }
        this.registerTask(new ItemIDAutoSaveTask().runTaskTimer((Plugin)Arkkit.get(), 200L, 200L), ItemIDAutoSaveTask.class);
        this.registerListener(new ItemIDListener());
        this.registerCommand(new CommandItemID("itemid", "/<command>", "ItemID admin command.", Arrays.asList("iid")));
    }

    @Override
    public void disable() {
        Arkkit.get().getConfig().set("settings.itemID.next_iid", (Object)(next_iid += 1000L));
        ArrayList<Long> llist = new ArrayList<Long>();
        for (long l : blacklist_iid) {
            llist.add(l);
        }
        Arkkit.get().getConfig().set("settings.itemID.blacklist_iid", llist);
        Arkkit.get().saveConfig();
    }

    public static boolean hasValidDurabilityForTracking(ItemStack i) {
        return !durabilityMap.containsKey((Object)i.getType()) || IntStream.of(durabilityMap.get((Object)i.getType())).anyMatch(x -> x == i.getDurability());
    }

    public static ItemStack assignItemID(InventoryClickEvent e, ItemStack i) {
        if (ItemID.getItemID(i) != -1L) {
            return i;
        }
        ItemMeta im = i.getItemMeta();
        if (im == null) {
            return i;
        }
        List lore = im.hasLore() ? im.getLore() : new ArrayList();
        long itemID = ItemID.generateNewItemID();
        lore.add(ChatColor.BLACK.toString() + "IID " + itemID);
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    public static long generateNewItemID() {
        return next_iid++;
    }

    public static boolean isItemBlacklisted(ItemStack item) {
        long itemID = ItemID.getItemID(item);
        if (itemID == -1L) {
            return false;
        }
        return blacklist_iid.contains(itemID);
    }

    public static long getItemID(ItemStack i) {
        if (i.hasItemMeta() && i.getItemMeta().hasLore()) {
            for (String s : i.getItemMeta().getLore()) {
                if (!s.startsWith(ChatColor.BLACK.toString() + "IID ")) continue;
                return Long.parseLong(ChatColor.stripColor((String)s.split(" ")[1]));
            }
        }
        return -1L;
    }

    public static ItemStack setItemID(ItemStack i, long iid) {
        ItemMeta im = i.getItemMeta();
        List<String> lore = im.hasLore() ? im.getLore() : new ArrayList();
        ArrayList<String> newLore = new ArrayList<String>();
        boolean replaced = false;
        for (String s : lore) {
            if (s.startsWith(ChatColor.BLACK.toString() + "IID ")) {
                newLore.add(ChatColor.BLACK.toString() + "IID " + iid);
                replaced = true;
                continue;
            }
            newLore.add(s);
        }
        if (!replaced) {
            newLore.add(ChatColor.BLACK.toString() + "IID " + iid);
        }
        im.setLore(newLore);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack removeItemID(ItemStack i) {
        ItemMeta im = i.getItemMeta();
        if (!im.hasLore()) {
            return i;
        }
        List<String> lore = im.getLore();
        ArrayList<String> newLore = new ArrayList<String>();
        for (String s : lore) {
            if (s.startsWith(ChatColor.BLACK.toString() + "IID ")) continue;
            newLore.add(s);
        }
        im.setLore(newLore);
        i.setItemMeta(im);
        return i;
    }

    public static void blacklistIID(long iid, String reason) {
        if (iid < 0L) {
            Bukkit.getLogger().info("[Arkkit (ItemID)] Skipping blacklist of IID " + iid + " for reason: " + reason + " due to iid<0");
            return;
        }
        if (!blacklist_iid.contains(iid)) {
            blacklist_iid.add(iid);
            Bukkit.getLogger().info("[Arkkit (ItemID)] Blacklisted IID " + iid + " for reason: " + reason);
        } else {
            Bukkit.getLogger().info("[Arkkit (ItemID)] Skipping blacklist of IID " + iid + " for reason: " + reason + " as it is already blacklisted.");
        }
    }

    public static boolean hasItemID(ItemStack i) {
        return ItemID.getItemID(i) != -1L;
    }

    public static boolean doesItemIDNeedToPersist(ItemStack i) {
        return i != null && (i.getMaxStackSize() == 1 && (GeneralUtil.isArmor((ItemStack)i) || GeneralUtil.isWeapon((ItemStack)i) || GeneralUtil.isTool((ItemStack)i)) || otherItemsToMonitor.contains((Object)i.getType()) && ItemID.hasValidDurabilityForTracking(i)) && (i.hasItemMeta() && i.getItemMeta().hasLore() || i.getType() == Material.MOB_SPAWNER || i.getTypeId() == 383);
    }

    public static boolean needsItemID(ItemStack i) {
        Material type = i != null ? i.getType() : null;
        return i != null && (i.getMaxStackSize() == 1 && (GeneralUtil.isArmor((ItemStack)i) || GeneralUtil.isWeapon((ItemStack)i) || GeneralUtil.isTool((ItemStack)i)) || otherItemsToMonitor.contains((Object)type) && ItemID.hasValidDurabilityForTracking(i)) && (i.hasItemMeta() && i.getItemMeta().hasLore() || type == Material.MOB_SPAWNER || i.getTypeId() == 383) && ItemID.getItemID(i) == -1L;
    }

    static {
        otherItemsToMonitor = new ArrayList<Material>(Arrays.asList(new Material[]{Material.PAPER, Material.MAGMA_CREAM, Material.BOOK, Material.EYE_OF_ENDER, Material.DIAMOND, Material.BONE, Material.ENDER_CHEST, Material.getMaterial((int)383), Material.NETHER_STAR, Material.TORCH, Material.EMERALD, Material.CHEST, Material.REDSTONE_TORCH_OFF, Material.MOB_SPAWNER, Material.EXP_BOTTLE, Material.PAINTING, Material.INK_SACK, Material.EMPTY_MAP, Material.ENCHANTED_BOOK, Material.FEATHER}));
        durabilityMap = new HashMap();
    }
}

