package org.arkhamnetwork.enchantments.enchantments.swords;

import com.astropvp.cc.packet.type.out.PacketOutHitSplat;
import com.astropvp.cc.player.CosmicPlayer;
import com.astropvp.cc.player.CosmicPlayers;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.concurrent.ThreadLocalRandom;

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
        final double mod = 4.2;
        if (random < chance) {
            if (!(e.getDamager() instanceof org.bukkit.entity.Player) || !(e.getEntity() instanceof org.bukkit.entity.Player))
                return;
            double finalDamage = e.getFinalDamage();
            Location place = e.getEntity().getLocation();
            DamageIndicatorUtils.sendDamageIndicator((Player) e.getEntity(), CC.AquaB + "* EXECUTE *", "EXECUTE");
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * mod);
            final Location l = target.getLocation();
        }

    }
}
