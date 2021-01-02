package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Devour extends CustomEnchantment {
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
            target.setMetadata("effectedByDevour", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) System.currentTimeMillis()));
            final Location l = target.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), Material.MYCEL.getId(), 2001);
        }
    }
}
