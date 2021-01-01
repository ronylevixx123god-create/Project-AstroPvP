package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.bukkit.entity.*;
import java.util.*;

public class Reflect extends CustomEnchantment
{
    public Reflect() {
        super("Enchant Reflect", GeneralUtil.armor, 3);
        this.max = 10;
        this.base = 25.0;
        this.interval = 10.0;
    }
    
    public void applyEquipEffect(final Player player, final int enchantLevel) {
        player.setMetadata("normal_reflect_enchant", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enchantLevel));
    }
    
    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        player.removeMetadata("normal_reflect_enchant", (Plugin)Enchantments.getInstance());
    }
    
    public static int getReflectLevel(final LivingEntity player, final String key) {
        final List<MetadataValue> found = (List<MetadataValue>)player.getMetadata(key + "_reflect_enchant");
        if (found != null && !found.isEmpty()) {
            return found.get(0).asInt();
        }
        return 0;
    }
}
