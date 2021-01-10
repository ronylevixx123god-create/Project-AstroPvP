/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.potion.Potion
 *  org.bukkit.potion.PotionType
 */
package me.ifamasssxd.cosmicduel.utils;

import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.ifamasssxd.cosmicduel.struct.CustomGKit;
import me.ifamasssxd.cosmicduel.struct.CustomKit;
import me.ifamasssxd.cosmicduel.struct.DuelPlayer;
import me.ifamasssxd.cosmicduel.struct.Kit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KitUtils {
    public static String ITEM_WATERMARK = (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "DUEL KIT ITEM";
    public static String ENVOY_WATERMARK = (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "DUEL ENVOY ITEM";
    private static List<ItemStack> edittableItems = Lists.newArrayList(new ItemStack(Material.POTION, 1, (short) 16421), new ItemStack(Material.POTION, 1, (short) 8226), new ItemStack(Material.POTION, 1, (short) 16418), new ItemStack(Material.POTION, 1, (short) 8233), new ItemStack(Material.POTION, 1, (short) 16425), new ItemStack(Material.POTION, 1, (short) 8259), new ItemStack(Material.ENDER_PEARL, 16), new ItemStack(Material.COOKED_BEEF, 64), new ItemStack(Material.GOLDEN_CARROT, 64));
    private static List<Material> armor = Lists.newArrayList(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS);

    public static GlobalKits getGlobalKitsFromItemName(String name) {
        for (GlobalKits kit : GlobalKits.values()) {
            if (!kit.getItemName().equals(name)) continue;
            return kit;
        }
        return null;
    }

    public static ItemStack addDuelWatermark(ItemStack item) {
        List<String> lore;
        ItemMeta im = (item = item.clone()).getItemMeta();
        List list = lore = im.hasLore() ? im.getLore() : Lists.newArrayList();
        if (lore.size() > 0 && ((String)lore.get(lore.size() - 1)).equals(ITEM_WATERMARK)) {
            return item;
        }
        lore.add(ITEM_WATERMARK);
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack addEnvoyWatermark(ItemStack item) {
        List<String> lore;
        ItemMeta im = (item = item.clone()).getItemMeta();
        List list = lore = im.hasLore() ? im.getLore() : Lists.newArrayList();
        if (lore.size() > 0 && lore.contains(ENVOY_WATERMARK)) {
            return item;
        }
        lore.add("");
        lore.add(ENVOY_WATERMARK);
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    public static boolean isInvalidGKit(GlobalKits gKit) {
        String name = gKit.name();
        return name.equals("Party") || name.equals("Enchantment") || name.equals("Masterbuilder") || name.equals("Independence") || name.equals("Experience") || name.equals("Library");
    }

    public static void openCustomKits(Player player, Kit kit) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, (int)9, (String)"Custom Kits");
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(player);
        List<CustomKit> customKits = duelPlayer.getCustomKits(kit);
        if (customKits == null || customKits.size() < 3) {
            inventory.addItem(new ItemStack[]{ItemBuilder.buildItem(Material.EMERALD, (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "Create Custom Kit", (Object)ChatColor.GRAY + "Click to create a new custom kit.")});
        }
        if (customKits != null) {
            int index = 1;
            for (CustomKit customKit : customKits) {
                ItemStack item = ItemBuilder.buildItem(customKit.getKitType().getMaterial(), (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "Custom Kit #" + index++, (Object)ChatColor.GRAY + "Left Click to modify this custom kit", (Object)ChatColor.GRAY + "Middle Click to set as default", (Object)ChatColor.GRAY + "Right Click to remove this custom kit", "", customKit.isDefault() ? (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "DEFAULT" : null);
                if (customKit.isDefault()) {
                    item.addUnsafeEnchantment(EnchantUtils.getCustomEnchant(), 1);
                }
                inventory.addItem(new ItemStack[]{item});
            }
        }
        inventory.setItem(inventory.getSize() - 1, ItemBuilder.buildItem(Material.ARROW, (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "Back", (Object)ChatColor.GRAY + "Return to Kit Selection"));
        duelPlayer.setKitModifying(kit);
        player.openInventory(inventory);
    }

    public static void openCustomGKit(Player player, GlobalKits kit) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, (int)9, (String)"Custom Kits");
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(player);
        List<CustomGKit> customKits = duelPlayer.getCustomGkits(kit);
        if (customKits == null || customKits.size() < 3) {
            inventory.addItem(new ItemStack[]{ItemBuilder.buildItem(Material.EMERALD, (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "Create Custom GKit", (Object)ChatColor.GRAY + "Click to create a new custom GKit.")});
        }
        if (customKits != null) {
            int index = 1;
            for (CustomGKit customKit : customKits) {
                ItemStack item = ItemBuilder.buildItem(customKit.getGlobalKit().getItemIcon(), (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "Custom Kit #" + index++, (Object)ChatColor.GRAY + "Left Click to modify this custom kit", (Object)ChatColor.GRAY + "Middle Click to set as default", (Object)ChatColor.GRAY + "Right Click to remove this custom kit", "", customKit.isDefault() ? (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "DEFAULT" : null);
                if (customKit.isDefault()) {
                    item.addUnsafeEnchantment(EnchantUtils.getCustomEnchant(), 1);
                }
                inventory.addItem(new ItemStack[]{item});
            }
        }
        inventory.setItem(inventory.getSize() - 1, ItemBuilder.buildItem(Material.ARROW, (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "Back", (Object)ChatColor.GRAY + "Return to Kit Selection"));
        duelPlayer.setGKitModifying(kit);
        player.openInventory(inventory);
    }

    private static boolean doesInventoryContain(ItemStack item, Inventory inventory) {
        for (ItemStack is : inventory) {
            if (is == null || is.getType() == Material.AIR || item.getType() != is.getType() || item.getDurability() != is.getDurability()) continue;
            return true;
        }
        return false;
    }

    public static void openCustomKitEditor(Player player, CustomKit kit) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, (int)9, (String)"Customize Your Kit");
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(player);
        ArrayList<ItemStack> toGive = Lists.newArrayList();
        toGive.addAll(edittableItems);
        for (ItemStack item : toGive) {
            if (!KitUtils.canEditKitItem(item = item.clone()) || KitUtils.doesInventoryContain(item, inventory)) continue;
            if (item.getType() == Material.POTION) {
                item.setAmount(1);
            }
            inventory.addItem(new ItemStack[]{item});
        }
        for (ItemStack item : kit.getKitItems()) {
            if ((item = KitUtils.addDuelWatermark(item.clone())).getType() == Material.POTION && item.getAmount() > 1) {
                while (item.getAmount() > 0) {
                    ItemStack clone = item.clone();
                    clone.setAmount(1);
                    player.getInventory().addItem(new ItemStack[]{clone});
                    item.setAmount(item.getAmount() - 1);
                }
                continue;
            }
            player.getInventory().addItem(new ItemStack[]{item});
        }
        duelPlayer.setCurrentlyModifying(kit);
        player.openInventory(inventory);
    }

    public static void openCustomGKitEditor(Player player, CustomGKit globalKits) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, (int)9, (String)"Customize Your Kit");
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(player);
        ArrayList<ItemStack> items = Lists.newArrayList();
        items.addAll(edittableItems);
        for (ItemStack item : items) {
            if (item.getType() == Material.POTION && Potion.fromItemStack((ItemStack)item).getType() == PotionType.REGEN || KitUtils.doesInventoryContain(item, inventory)) continue;
            inventory.addItem(new ItemStack[]{item});
        }
        for (ItemStack item : globalKits.getGKitItems()) {
            if (!DuelUtils.isValidKitItem(item)) continue;
            player.getInventory().addItem(new ItemStack[]{item});
        }
        for (ItemStack is : globalKits.getItems()) {
            is = KitUtils.addDuelWatermark(is);
            player.getInventory().addItem(new ItemStack[]{is});
        }
        player.updateInventory();
        duelPlayer.setGKitCurrentlyModifying(globalKits);
        player.openInventory(inventory);
    }

    public static void openNewCustomGKit(Player player, GlobalKits gKit) {
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(player);
        CustomGKit customKit = new CustomGKit(gKit);
        duelPlayer.addCustomGKit(customKit);
        if (duelPlayer.getCustomGkits(gKit).size() == 1) {
            customKit.setDefault(true);
        }
        KitUtils.openCustomGKitEditor(player, customKit);
    }

    public static void openNewCustomKit(Player player, Kit kit) {
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(player);
        CustomKit customKit = new CustomKit(duelPlayer.getKitModifying());
        customKit.setKitItems(kit.getItems());
        duelPlayer.addCustomKit(customKit);
        if (duelPlayer.getCustomKits(kit).size() == 1) {
            customKit.setDefault(true);
        }
        KitUtils.openCustomKitEditor(player, customKit);
    }

    private static boolean canEditKitItem(ItemStack item) {
        return !DuelUtils.isWeapon(item) && !DuelUtils.isArmor(item) && item.getType() != Material.GOLDEN_APPLE && item.getType() != Material.ARROW;
    }

    public static List<ItemStack> getMaxedEnchantsFromGKit(GlobalKits globalKit) {
        List<ItemStack> gkitItems = CosmicGlobalKitsAPI.getGlobalKitItems((GlobalKits)globalKit, (String)null);
        for (ItemStack item : gkitItems) {
            Map<CustomEnchantment, Integer> customEnchants = EnchantmentAPI.getEnchantments((ItemStack)(item = KitUtils.addDuelWatermark(item)));
            if (customEnchants == null) continue;
            for (CustomEnchantment customEnchant : customEnchants.keySet()) {
                customEnchant.addToItem(item, customEnchant.getMaxLevel());
            }
        }
        for (Material type : armor) {
            if (KitUtils.containsType(gkitItems, type)) continue;
            gkitItems.add(new CustomItem(type).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).buildItem());
        }
        if (!KitUtils.containsType(gkitItems, Material.DIAMOND_SWORD) && !KitUtils.containsType(gkitItems, Material.DIAMOND_AXE)) {
            gkitItems.add(new CustomItem(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).buildItem());
        }
        if (KitUtils.containsType(gkitItems, Material.BOW) && !KitUtils.containsType(gkitItems, Material.ARROW)) {
            gkitItems.add(new ItemStack(Material.ARROW, 1));
        }
        return gkitItems;
    }

    private static boolean containsType(List<ItemStack> items, Material material) {
        for (ItemStack gkitItem : items) {
            if (gkitItem.getType() != material) continue;
            return true;
        }
        return false;
    }
}

