/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class HeroicReflect
extends HeroicCustomEnchantment {
    public HeroicReflect() {
        super("Heroic Enchant Reflect", "Enchant Reflect", GeneralUtil.armor, 10);
        this.max = 10;
        this.base = 25.0;
        this.interval = 10.0;
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        player.setMetadata("heroic_reflect_enchant", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enchantLevel));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        player.removeMetadata("heroic_reflect_enchant", (Plugin)Enchantments.getInstance());
    }
}

