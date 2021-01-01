package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

public class Devour extends CustomEnchantment
{
    public Devour() {
        super("Devour", GeneralUtil.axe, 2);
        this.max = 4;
        this.base = 10.0;
        this.interval = 8.0;
    }
    
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0 && user instanceof Player && Bleed.getBleedStack(target) > 0) {
            if (GeneralUtil.isEffectedByRage(target)) {
                return;
            }
            final double mod = Bleed.getBleedStack(target) * 0.015 + enchantLevel * 0.1 + 1.0;
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * mod);
            target.setMetadata("effectedByDevour", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)System.currentTimeMillis()));
            final Location l = target.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), Material.MYCEL.getId(), 2001);
        }
    }
}
