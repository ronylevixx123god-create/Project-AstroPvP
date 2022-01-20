package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.*;
import net.astropvp.damagehandler.AstroDamageHandler;
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
        if (!event.isCancelled() && event.getDamage() > 0.0 && user instanceof Player && Bleed.getBleedStack(target) > 0) {
            if (GeneralUtil.isEffectedByRage(target)) {
                return;
            }
            double bleedStacks = Bleed.getBleedStack(target);
            if (bleedStacks >= 100.0) {
                bleedStacks = 99.0;
            }
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(bleedStacks + 4.0, event, "&6&lDevour");
            target.setMetadata("effectedByDevour", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis()));
            final Location l = target.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), Material.MYCEL.getId(), 2001);
        }
    }
}
