package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;

public class Ghost extends CustomEnchantment
{
    public Ghost() {
        super("Ghost", GeneralUtil.armor, 9);
        this.max = 3;
        this.base = 25.0;
        this.interval = 10.0;
    }
    
    public void applyEquipEffect(final Player player, final int enchantLevel) {
        player.setMetadata("ghostEnchantment", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enchantLevel));
    }
    
    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        player.removeMetadata("ghostEnchantment", (Plugin)Enchantments.getInstance());
    }
}
