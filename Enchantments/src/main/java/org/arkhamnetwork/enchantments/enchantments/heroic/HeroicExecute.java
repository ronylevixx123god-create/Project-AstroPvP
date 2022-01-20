package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HeroicExecute extends HeroicCustomEnchantment{
    public HeroicExecute() {
        super("Permanent Execute","Execute", GeneralUtil.swords, 2);
        this.max = 7;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity target, int level, EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        if (target.getHealth() > 12.0) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.05 * level;
        if (random < chance) {
            int mod = 100;
            if (level == 1) {
                mod = 200;
            }
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage((double)(level * mod), e, "&d&lPermanent Execute");
            final Location l = target.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), Material.REDSTONE_WIRE.getId(), 2001);
            DamageIndicatorUtils.sendDamageIndicator((Player) e.getEntity(), CC.RedB + "* PERMANENT EXECUTE *", "EXECUTE");

        }
    }
}
