/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  com.rit.sucy.service.ERomanNumeral
 *  org.arkhamnetwork.enchantments.util.EnchantmentDescriptions
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.ifamasssxd.cosmicduel.godset;

import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.service.ERomanNumeral;
import me.ifamasssxd.cosmicduel.listeners.GodsetCreationListener;
import me.ifamasssxd.cosmicduel.utils.*;
import org.arkhamnetwork.enchantments.util.EnchantmentDescriptions;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class CustomGodSet {
    private LinkedHashMap<Material, List<String>> customEnchants = new LinkedHashMap();
    private Material materialEditting = null;
    private EditState editState = EditState.CATEGORIES;
    private boolean changed = false;
    private static final HashMap<Material, Integer> materialSlots = new HashMap<Material, Integer>(){
        {
            this.put(Material.DIAMOND_HELMET, 9);
            this.put(Material.DIAMOND_CHESTPLATE, 9);
            this.put(Material.DIAMOND_LEGGINGS, 9);
            this.put(Material.DIAMOND_BOOTS, 9);
            this.put(Material.DIAMOND_SWORD, 9);
            this.put(Material.DIAMOND_AXE, 9);
            this.put(Material.BOW, 9);
        }
    };
    private List<String> blocked = Lists.newArrayList("lifesteal", "inversion", "explosive", "anti gravity");

    public List<String> getCustomEnchants(Material mat) {
        if (this.customEnchants.containsKey((Object)mat)) {
            List<String> list = this.customEnchants.get((Object)mat);
            return list;
        }
        List<String> list = this.customEnchants.put(mat, Lists.newArrayList());
        return list;
    }

    public int getMaxSlots(Material material) {
        if (!materialSlots.containsKey((Object)material)) {
            return 0;
        }
        int n = materialSlots.get((Object)material);
        return n;
    }

    public int getSlotsUsed(Material material) {
        List<String> enchants = this.customEnchants.get((Object)material);
        if (enchants == null) {
            return 0;
        }
        if (enchants.size() == 0) {
            return 0;
        }
        int pointsUsed = 0;
        for (String enchant : enchants) {
            int enchantTier = TieredEnchantments.getEnchantmentTier((String)enchant);
            pointsUsed += EnchantSlots.getSlots(enchantTier);
        }
        return pointsUsed;
    }

    public void checkOldGodset() {
        if (this.customEnchants == null) {
            return;
        }
        this.customEnchants.forEach((edit, lists) -> {
            int pointsUsed = 0;
            int maxSlots = this.getMaxSlots((Material)edit);
            List<String> currentEnchants = this.customEnchants.get(edit);
            ArrayList toRemove = Lists.newArrayList();
            if (currentEnchants == null) {
                return;
            }
            if (maxSlots <= 0) {
                return;
            }
            Iterator<String> iterator = currentEnchants.iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    toRemove.forEach(currentEnchants::remove);
                    return;
                }
                String enchant = iterator.next();
                int enchantTier = TieredEnchantments.getEnchantmentTier((String)enchant);
                int slotsToUse = EnchantSlots.getSlots(enchantTier);
                if (pointsUsed + slotsToUse > maxSlots) {
                    toRemove.add(enchant);
                    Bukkit.getLogger().info("Removing old enchant: " + enchant + " as pointsUsed > " + maxSlots + " for " + (Object)edit + " points: " + pointsUsed);
                    continue;
                }
                pointsUsed += slotsToUse;
            }
        });
    }

    public Inventory getEditInventory() {
        LinkedList<ItemStack> items = this.getKitItems(true, true);
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, (String)"Duel God Set");
        for (ItemStack item : items) {
            ItemMeta im = (item = item.clone()).getItemMeta();
            List<String> lore = im.hasLore() ? im.getLore() : Lists.newArrayList();
            int maxSlots = this.getMaxSlots(item.getType());
            int usedSlots = this.getSlotsUsed(item.getType());
            int slotsLeft = maxSlots - usedSlots;
            for (int i = 0; i < slotsLeft; ++i) {
                lore.add((Object)ChatColor.GRAY + ChatColor.ITALIC.toString() + "<EMPTY>");
            }
            if (maxSlots > 0) {
                lore.add("");
                lore.add((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + (maxSlots - usedSlots) + (Object)ChatColor.GREEN + " Enchant Slots Left");
                lore.add("");
                lore.add((Object)ChatColor.GRAY + "Left-Click to add enchants.");
                lore.add((Object)ChatColor.GRAY + "Middle-Click to clear custom enchants.");
                im.setLore(lore);
                item.setItemMeta(im);
            }
            inventory.addItem(new ItemStack[]{item});
        }
        return inventory;
    }

    public Inventory getBookRarityMenu() {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, (String)GodsetCreationListener.BOOK_RARITY_INVENTORY_NAME);
        HashMap<EnchantSlots, List<CustomEnchantment>> getSortedEnchants = this.getSortedEnchants();
        int index = 2;
        for (EnchantSlots slot : EnchantSlots.values()) {
            ArrayList lore = Lists.newArrayList(new String[]{(Object)ChatColor.GRAY + "Each Enchantment of this rarity", (Object)ChatColor.GRAY + "costs " + (Object)slot.getNameColor() + ChatColor.UNDERLINE.toString() + slot.getSlotCost() + (Object)ChatColor.GRAY + " Enchant Slots"});
            if (getSortedEnchants.size() > 0 && getSortedEnchants.containsKey((Object)slot)) {
                lore.add("");
                lore.add((Object)slot.getNameColor() + ChatColor.BOLD.toString() + "Applied Enchants:");
                for (CustomEnchantment enchant : getSortedEnchants.get((Object)slot)) {
                    lore.add((Object)slot.getNameColor() + "* " + enchant.name() + " " + ERomanNumeral.numeralOf((int)enchant.getMaxLevel()));
                }
            }
            ItemStack item = ItemBuilder.buildItem(Material.BOOK, (short)0, (Object)slot.getNameColor() + slot.getDisplayName() + " Enchantments", lore);
            inventory.setItem(index++, item);
        }
        return inventory;
    }

    public HashMap<EnchantSlots, List<CustomEnchantment>> getSortedEnchants() {
        Material edit = this.getMaterialEditting();
        HashMap<EnchantSlots, List<CustomEnchantment>> sorted = new HashMap<EnchantSlots, List<CustomEnchantment>>();
        this.customEnchants.entrySet().forEach(entry -> {
            if (entry.getKey() != edit) {
                return;
            }
            for (String enchantName : entry.getValue()) {
                EnchantSlots slot;
                CustomEnchantment enchant = EnchantmentAPI.getEnchantment((String)enchantName);
                if (enchant == null || (slot = EnchantSlots.getEnchantRarity(TieredEnchantments.getEnchantmentTier((String)enchant.name()))) == null) continue;
                if (sorted.containsKey((Object)slot)) {
                    ((List)sorted.get((Object)slot)).add(enchant);
                    continue;
                }
                sorted.put(slot, Lists.newArrayList(enchant));
            }
        });
        return sorted;
    }

    public Inventory getEnchantmentInventory(EnchantSlots rarity, Material editting) {
        List<CustomEnchantment> enchants = this.getCustomEnchantsAllowed(editting);
        List<CustomEnchantment> ofRarity = enchants.stream().filter(ench -> EnchantSlots.getEnchantRarity(TieredEnchantments.getEnchantmentTier((String)ench.name())) == rarity).collect(Collectors.toList());
        Collections.sort(ofRarity, (o1, o2) -> o1.name().compareTo(o2.name()));
        List<CustomEnchantment> currentEnchants = this.getCurrentEnchants(editting);
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, (String)"Duel Enchants");
        for (CustomEnchantment enchant : ofRarity) {
            List<String> lore = StringUtils.trimLines((Object)ChatColor.GRAY + EnchantmentDescriptions.getDescription((CustomEnchantment)enchant), ChatColor.GRAY, 38);
            lore.add("");
            lore.add((Object)rarity.getNameColor() + "Uses " + (Object)rarity.getNameColor() + (Object)ChatColor.UNDERLINE + rarity.getSlotCost() + (Object)rarity.getNameColor() + " Enchant Slots");
            ItemStack item = ItemBuilder.buildItem(Material.BOOK, 1, (short)0, (Object)rarity.getNameColor() + ChatColor.BOLD.toString() + enchant.name() + " " + ERomanNumeral.numeralOf((int)enchant.getMaxLevel()), lore);
            if (currentEnchants.contains((Object)enchant)) {
                item.addUnsafeEnchantment(EnchantUtils.getCustomEnchant(), 1);
            }
            inventory.addItem(new ItemStack[]{item});
        }
        return inventory;
    }

    public List<CustomEnchantment> getCurrentEnchants(Material mat) {
        ArrayList enchants = Lists.newArrayList();
        ArrayList<CustomEnchantment> toRemove = Lists.newArrayList();
        List<String> stored = this.customEnchants.get((Object)mat);
        Iterator<String> iterator = stored.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                toRemove.forEach(en -> stored.remove(en.name()));
                return enchants;
            }
            String enchant = iterator.next();
            CustomEnchantment enchantment = EnchantmentAPI.getEnchantment((String)enchant);
            if (enchantment == null) continue;
            if (enchantment.heroicEnchantment) {
                toRemove.add(enchantment);
                continue;
            }
            enchants.add(enchantment);
        }
    }

    public LinkedList<ItemStack> getKitItems(boolean applyEnchants, boolean showEmptySlots) {
        LinkedList items = Lists.newLinkedList();
        if (this.customEnchants.size() == 0) {
            this.getBaseItems().forEach(is -> this.customEnchants.put(is.getType(), Lists.newArrayList()));
        }
        this.customEnchants.forEach((mat, enchants) -> {
            ItemStack item = new ItemStack(mat);
            if (!DuelUtils.isValidRankedMaterial(item)) {
                return;
            }
            if (applyEnchants) {
                for (String enchant : enchants) {
                    CustomEnchantment enchantment = EnchantmentAPI.getEnchantment((String)enchant);
                    if (enchantment == null || this.blocked.contains(enchantment.name().toLowerCase()) || enchantment.heroicEnchantment) continue;
                    enchantment.addToItem(item, enchantment.getMaxLevel());
                }
                if (DuelUtils.isArmor(item)) {
                    item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                }
                if (DuelUtils.isWeapon(item)) {
                    if (item.getType() == Material.BOW) {
                        item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 5);
                    } else {
                        item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
                    }
                }
                item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            }
            items.add(item);
        });
        return items;
    }

    public List<CustomEnchantment> getCustomEnchantsAllowed(Material material) {
        ArrayList<CustomEnchantment> customEnchants = new ArrayList<CustomEnchantment>();
        ItemStack item = new ItemStack(material);
        customEnchants.addAll(EnchantmentAPI.getEnchantments().stream().filter(enchantment -> {
            if (!enchantment.canEnchantOnto(item)) {
                return false;
            }
            if (enchantment.heroicEnchantment) {
                return false;
            }
            return !this.blocked.contains(enchantment.name().toLowerCase());
        }).collect(Collectors.toList()));
        return customEnchants;
    }

    public LinkedList<ItemStack> getBaseItems() {
        return new LinkedList<ItemStack>(Arrays.asList(new ItemStack[]{new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.DIAMOND_AXE), new ItemStack(Material.BOW)}));
    }

    public LinkedHashMap<Material, List<String>> getCustomEnchants() {
        return this.customEnchants;
    }

    public Material getMaterialEditting() {
        return this.materialEditting;
    }

    public void setMaterialEditting(Material materialEditting) {
        this.materialEditting = materialEditting;
    }

    public EditState getEditState() {
        return this.editState;
    }

    public void setEditState(EditState editState) {
        this.editState = editState;
    }

    public boolean isChanged() {
        return this.changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}

