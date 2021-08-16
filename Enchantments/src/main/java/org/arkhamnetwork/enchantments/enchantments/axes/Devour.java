package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralCooldown;
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
        this.base = 10.0D;
        this.interval = 8.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage() > 0.0D && user instanceof org.bukkit.entity.Player)
            if (Bleed.getBleedStack(target) > 0) {
                if (GeneralUtil.isEffectedByRage(target))
                    return;
                double mod = Bleed.getBleedStack(target) * 0.015D + enchantLevel * 0.1D + 1.0D;
                event.setDamage(event.getDamage() * mod);
                target.setMetadata("effectedByDevour", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Long.valueOf(System.currentTimeMillis())));
                Location l = target.getLocation();

            }
    }
}
