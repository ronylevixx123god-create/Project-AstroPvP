package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.axes.Bleed;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class HeroicDevour extends HeroicCustomEnchantment {
    public HeroicDevour() {
        super("Vampiric Devour","Devour", GeneralUtil.axe, 2);
        this.max = 4;
        this.base = 10.0;
        this.interval = 8.0;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage() > 0.0 && user instanceof Player && Bleed.getBleedStack(target) > 0) {
            if (GeneralUtil.isEffectedByRage(target)) {
                return;
            }
            if (!GeneralUtil.isSword(((Player) user).getItemInHand())) {

                double mod = (double) Bleed.getBleedStack(target) * 0.015 + (double) enchantLevel * 0.1 + 1.0;
                event.setDamage(event.getDamage() * mod);
                target.setMetadata("effectedByDevour", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) System.currentTimeMillis()));
                Location l = target.getLocation();
                if (Math.random() < 0.05)
                    user.setHealth(user.getHealth() + 1.0);
            }
        }
    }
}
