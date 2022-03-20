package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.enchantments.axes.*;
import org.bukkit.metadata.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

public class HeroicDevour extends CustomEnchantment
{
    public HeroicDevour() {
        super("Vampiric Devour", GeneralUtil.axe, 2);
        this.max = 4;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
            this.heroicEnchantment = true;
            this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Devour");
        }, 2L);
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage() > 0.0D && user instanceof Player && Bleed.getBleedStack(target) > 0) {
            if (GeneralUtil.isEffectedByRage(target)) {
                return;
            }

            if (Math.random() > 0.4D) {
                return;
            }

            double mod = (double)Bleed.getBleedStack(target) * 0.015D + (double)enchantLevel * 0.1D + 1.0D;
            event.setDamage(event.getDamage() * mod);
            target.setMetadata("effectedByDevour", new FixedMetadataValue(Enchantments.getInstance(), System.currentTimeMillis()));
            Location l = target.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), Material.MYCEL.getId(), 2001);
        }

    }
}
