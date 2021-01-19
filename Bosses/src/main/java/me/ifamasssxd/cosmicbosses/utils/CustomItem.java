package me.ifamasssxd.cosmicbosses.utils;

import org.bukkit.inventory.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.meta.*;

public class CustomItem
{
    ItemStack is;
    
    public CustomItem(final ItemStack is) {
        this.is = is;
    }
    
    public CustomItem addEnchant(final Enchantment enchant, final int level) {
        this.is.addUnsafeEnchantment(enchant, level);
        return this;
    }
    
    public ItemStack build() {
        return this.is;
    }
    
    public CustomItem setUnbreakable(final boolean unbreak) {
        final ItemMeta im = this.is.getItemMeta();
        im.spigot().setUnbreakable(unbreak);
        this.is.setItemMeta(im);
        return this;
    }
}
