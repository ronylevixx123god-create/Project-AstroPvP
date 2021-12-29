package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
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
        if (target.getHealth() > 6.0) {
            return;
        }
        if (GeneralUtil.isEffectedByRage(target)) {
            return;
        }
        double random = Math.random();
        double chance = 0.04 * (double)level;
        double mod = 5.0;
        if (random < chance) {
            e.setDamage(e.getDamage() * mod);
            DamageIndicatorUtils.sendDamageIndicator((Player) e.getEntity(), CC.RedB + "* PERMANENT EXECUTE *", "EXECUTE");

            Location l = target.getLocation();
            WorldUtil.sendWorldEffect((Block)l.getBlock(), (int) Material.REDSTONE_WIRE.getId(), (int)2001);
        }
    }
}
