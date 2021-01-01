package org.arkhamnetwork.enchantments.mastery;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;

public class MasteryExplosivesExpert extends MasteryCustomEnchantment
{
    public MasteryExplosivesExpert() {
        super("Explosives Expert", GeneralUtil.armor, 1);
        this.max = 1;
    }
    
    public void applyEquipEffect(final Player player, final int enchantLevel) {
        player.setMetadata("explosivesExpertEnchantment", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enchantLevel));
    }
    
    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        player.removeMetadata("explosivesExpertEnchantment", (Plugin)Enchantments.getInstance());
    }
}
