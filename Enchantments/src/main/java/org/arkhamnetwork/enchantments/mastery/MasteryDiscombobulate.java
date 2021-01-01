package org.arkhamnetwork.enchantments.mastery;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;

public class    MasteryDiscombobulate extends MasteryCustomEnchantment
{
    public MasteryDiscombobulate() {
        super("Discombobulate", GeneralUtil.armor, 10);
        this.max = 1;
    }
    
    public void applyEquipEffect(final Player player, final int enchantLevel) {
        player.setMetadata("discombobulateEnchantment", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enchantLevel));
    }
    
    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        player.removeMetadata("discombobulateEnchantment", (Plugin)Enchantments.getInstance());
    }
}
