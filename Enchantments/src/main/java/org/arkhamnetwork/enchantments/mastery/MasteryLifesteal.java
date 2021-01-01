package org.arkhamnetwork.enchantments.mastery;

import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import com.google.common.collect.*;
import org.arkhamnetwork.enchantments.events.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import com.rit.sucy.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;
import java.util.*;

public class MasteryLifesteal extends MasteryCustomEnchantment
{
    public MasteryLifesteal() {
        super("Chain Lifesteal", GeneralUtil.armor, 10);
        this.max = 5;
        this.armorSet = "DEATH_KNIGHT";
    }
    
    @Override
    public void applyDamagedOtherPlayer(final Player damager, final Player damaged, final EntityDamageByEntityEvent event, final int enchantLevel) {
        if (Math.random() <= enchantLevel * 0.05) {
            if (MaskType.hasEquipped(damaged, "NECROMANCER") || !this.canEffectPlayer(damaged)) {
                return;
            }
            final double radius = 1 + enchantLevel / 2;
            final int maxTargets = (int)(1.0 + Math.ceil(enchantLevel / 2));
            int scanned = 0;
            final List<Entity> nearby = (List<Entity>)Lists.newArrayList((Iterable)damaged.getNearbyEntities(radius, radius, radius));
            nearby.add((Entity)damaged);
            for (final Entity ent : nearby) {
                if (ent instanceof Player) {
                    final Player nearbyPlayer = (Player)ent;
                    if (nearbyPlayer.equals(damager)) {
                        continue;
                    }
                    if (!this.canEffectPlayer(nearbyPlayer) || MaskType.hasEquipped(nearbyPlayer, "NECROMANCER")) {
                        continue;
                    }
                    if (GeneralUtil.isAtleastTruce(damaged, nearbyPlayer)) {
                        continue;
                    }
                    if (!new CustomEnchantProcOnEntityEvent((LivingEntity)damager, (LivingEntity)damaged, (Event)event, this, enchantLevel).callEvent()) {
                        continue;
                    }
                    if (scanned >= maxTargets) {
                        break;
                    }
                    ++scanned;
                    final double toSteal = 0.5 + this.random.nextDouble() * enchantLevel;
                    GeneralUtil.reducePlayerHealth(nearbyPlayer, toSteal);
                    GeneralUtil.healPlayer((LivingEntity)damaged, toSteal);
                    WorldUtil.sendWorldEffect(nearbyPlayer.getEyeLocation().getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
                    if (nearbyPlayer.isDead()) {
                        event.setCancelled(true);
                        return;
                    }
                    nearbyPlayer.playEffect(EntityEffect.HURT);
                }
            }
        }
    }
}
