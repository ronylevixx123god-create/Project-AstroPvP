package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.arkhamnetwork.enchantments.enchantments.axes.*;

public class DeepWounds extends CustomEnchantment
{
    public DeepWounds() {
        super("Deep Wounds", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 15.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        playerHit.setMetadata("deepWounds", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(System.currentTimeMillis() + level * 1500)));
        final double mod = Bleed.getBleedStack(playerHit) * 0.005 + level * 0.01 + 1.0;
        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * mod);
    }
}
