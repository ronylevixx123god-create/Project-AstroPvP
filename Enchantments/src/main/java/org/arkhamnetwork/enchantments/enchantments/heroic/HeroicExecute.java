package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeroicExecute extends HeroicCustomEnchantment {
    public HeroicExecute() {
        super("Permanent Execute", "Execute", GeneralUtil.swords, 2);
        this.max = 7;
    }

    public void applyEffect(LivingEntity player, LivingEntity target, int level, EntityDamageByEntityEvent e) {
        if (!(target instanceof org.bukkit.entity.Player))
            return;
        if (target.getHealth() > 8.0D)
            return;
        if (GeneralUtil.isEffectedByRage(target))
            return;
        double random = Math.random();
        double chance = 0.04D * level;
        double mod = 3.0D + (level/2D);
        if (random < chance) {
            DamageIndicatorUtils.sendDamageIndicator((Player) e.getEntity(), CC.RedB + "* PERMANENT EXECUTE *", "EXECUTE");
            e.setDamage(e.getDamage() * mod);
            Location l = target.getLocation();
        }
    }
}
