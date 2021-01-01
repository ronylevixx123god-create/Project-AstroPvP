package org.arkhamnetwork.enchantments.mastery;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;

public class MasteryLavaStrider extends MasteryCustomEnchantment
{
    public MasteryLavaStrider() {
        super("Lava Strider", GeneralUtil.boots, 10);
        this.max = 1;
    }
    
    public void applyEquipEffect(final Player player, final int enchantLevel) {
        player.setMetadata("lavaStriderEnchantment", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enchantLevel));
    }
    
    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        player.removeMetadata("lavaStriderEnchantment", (Plugin)Enchantments.getInstance());
    }
}
