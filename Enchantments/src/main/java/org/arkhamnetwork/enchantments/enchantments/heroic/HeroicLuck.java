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

public class HeroicLuck
extends HeroicCustomEnchantment {
    public HeroicLuck() {
        super("Infinite Luck", "Lucky", GeneralUtil.armor, 2);
        this.max = 5;
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        player.setMetadata("infiniteLucky", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enchantLevel));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        player.removeMetadata("infiniteLucky", (Plugin)Enchantments.getInstance());
    }
}

