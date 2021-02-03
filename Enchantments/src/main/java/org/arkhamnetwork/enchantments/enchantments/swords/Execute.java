package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.damageindicators.utils.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Execute extends CustomEnchantment {
    public Execute() {
        super("Execute", GeneralUtil.swords, 2);
        this.max = 7;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity target, final int level, final EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        if (target.getHealth() > 12.0) {
            return;
        }
        if (GeneralUtil.isEffectedByRage(target)) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.1 * level;
        final double mod = 5.2;
        if (random < chance) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * mod);
            final Location l = target.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), Material.REDSTONE_WIRE.getId(), 2001);
        }

        try {
            if (DamageIndicatorUtils.isPlayerIndicating((Player) player)) {
                DamageIndicatorUtils.createDamageIndicator((Player) player,
                        target.getLocation().add(1.0D, 2.0D, 0.0D),
                        new String[] { CC.translate("&b&l* EXECUTE *") });
            }
        } catch (Exception ex) {

        }
    }
}
