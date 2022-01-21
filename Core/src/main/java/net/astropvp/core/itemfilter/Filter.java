/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package net.astropvp.core.itemfilter;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.astropvp.core.AstroCore;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Filter {
    boolean enabled;
    HashMap<Integer, Set<Short>> filteredItems = new HashMap();

    public boolean canPickupItem(ItemStack item, Material knownMaterial) {
        Set<Short> dataAllowed = this.filteredItems.get(knownMaterial != null ? knownMaterial.getId() : item.getTypeId());
        Material type = item.getType();
        String name = type.name();
        if (dataAllowed != null) {
            return name.endsWith("_HELMET") || name.endsWith("_CHESTPLATE") || name.endsWith("_LEGGINGS") || name.endsWith("_BOOTS") || name.endsWith("_SWORD") || name.endsWith("_AXE") || name.endsWith("BOW") || name.endsWith("_HOE") || type == Material.MOB_SPAWNER || type == Material.POTION || type == Material.MONSTER_EGG || dataAllowed.contains(item.getDurability());
        }
        if ((name.startsWith("RECORD_") || type == Material.GOLD_RECORD) && this.filteredItems.containsKey(Material.GREEN_RECORD.getId())) {
            return true;
        }
        ItemCategory categoryBelondsToo = AstroCore.getInstance().getFilterManager().getCategoryFromItem(item, type);
        return categoryBelondsToo == null && this.filteredItems.containsKey(Material.DRAGON_EGG.getId());
    }

    public HashMap<ItemCategory, List<ItemStack>> getItemMap() {
        HashMap<ItemCategory, List<ItemStack>> retr = new HashMap<ItemCategory, List<ItemStack>>();
        FilterManager filterManager = AstroCore.getInstance().getFilterManager();
        for (Map.Entry<Integer, Set<Short>> entry : this.getFilteredItems().entrySet()) {
            for (short dura : entry.getValue()) {
                ItemStack item = new ItemStack(Material.getMaterial((int)entry.getKey()), 1, dura);
                for (ItemCategory category : ItemCategory.values()) {
                    if (!filterManager.filterableItems.get((Object)category).contains((Object)item)) continue;
                    ArrayList<ItemStack> items = (ArrayList<ItemStack>) retr.get(category);
                    if (items == null) {
                        items = Lists.newArrayList();
                    }
                    if (!items.contains((Object)item)) {
                        items.add(item);
                    }
                    retr.put(category, items);
                }
            }
        }
        return retr;
    }

    public void addFilterItem(ItemStack item) {
        if ((item.getType() == Material.MOB_SPAWNER || item.getType() == Material.POTION || item.getType() == Material.MONSTER_EGG) && this.filteredItems.containsKey(item.getTypeId())) {
            return;
        }
        Set<Short> shorts = this.filteredItems.get(item.getTypeId());
        if (shorts == null) {
            shorts = new HashSet<Short>();
        }
        if (!shorts.contains(item.getDurability())) {
            shorts.add(item.getDurability());
            this.filteredItems.put(item.getTypeId(), shorts);
        }
    }

    public void removeItemFromFilter(ItemStack item) {
        Set<Short> shorts = this.filteredItems.get(item.getTypeId());
        if (shorts == null) {
            return;
        }
        shorts.remove(item.getDurability());
        if (shorts.size() == 0) {
            this.filteredItems.remove(item.getTypeId());
            return;
        }
        this.filteredItems.put(item.getTypeId(), shorts);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public HashMap<Integer, Set<Short>> getFilteredItems() {
        return this.filteredItems;
    }
}

