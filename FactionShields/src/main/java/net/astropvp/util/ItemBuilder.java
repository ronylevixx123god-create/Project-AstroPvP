// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.util;

import java.util.Collections;
import java.util.ArrayList;
import org.apache.commons.lang3.tuple.Pair;
import java.util.List;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;

public class ItemBuilder
{
    private ItemStack item;
    private ItemMeta meta;
    
    public ItemBuilder(final Material material) {
        this.item = new ItemStack(material);
        this.meta = this.item.getItemMeta();
    }
    
    public ItemBuilder(final ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }
    
    public ItemBuilder(final Material material, final DyeColor color) {
        this.item = new ItemStack(material, 1, (short)color.ordinal());
        this.meta = this.item.getItemMeta();
    }
    
    public ItemBuilder setLore(final List<String> lore, final List<Pair<String, String>> placeholders) {
        final List<String> updated = new ArrayList<String>();
        final List<String> loreCopy = new ArrayList<String>(lore);
        for (String temp : loreCopy) {
            final String line = temp;
            for (final Pair<String, String> pair : placeholders) {
                temp = temp.replaceAll((String)pair.getKey(), (String)pair.getValue());
            }
            updated.add(temp);
        }
        this.meta.setLore((List)updated);
        return this;
    }
    
    public ItemBuilder setName(final String name) {
        this.meta.setDisplayName(name);
        return this;
    }
    
    public ItemBuilder setLore(final String lore) {
        this.meta.setLore((List)Collections.singletonList(lore));
        return this;
    }
    
    public ItemBuilder setLore(final List<String> lore) {
        this.meta.setLore((List)lore);
        return this;
    }
    
    public ItemStack build() {
        this.item.setItemMeta(this.meta);
        return this.item;
    }
}
