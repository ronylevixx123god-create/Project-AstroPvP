/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.inventory.meta;

import java.util.List;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;

public interface ItemMeta
extends Cloneable,
ConfigurationSerializable {
    public boolean hasDisplayName();

    public String getDisplayName();

    public void setDisplayName(String var1);

    public boolean hasLore();

    public List<String> getLore();

    public void setLore(List<String> var1);

    public boolean hasEnchants();

    public boolean hasEnchant(Enchantment var1);

    public int getEnchantLevel(Enchantment var1);

    public Map<Enchantment, Integer> getEnchants();

    public boolean addEnchant(Enchantment var1, int var2, boolean var3);

    public boolean removeEnchant(Enchantment var1);

    public boolean hasConflictingEnchant(Enchantment var1);

    public ItemMeta clone();
}

