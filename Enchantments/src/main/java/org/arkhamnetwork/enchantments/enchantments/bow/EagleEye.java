package org.arkhamnetwork.enchantments.enchantments.bow;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.*;

public class EagleEye extends CustomEnchantment
{
    public EagleEye() {
        super("Eagle Eye", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (Math.random() < 0.05 * level + 0.2) {
            if (e.isCancelled()) {
                return;
            }
            if (!(playerHit instanceof Player)) {
                return;
            }
            if (playerHit.hasMetadata("lastArrowDamageEvent")) {
                final long minDelayMilliseconds = level * 200;
                final long last = playerHit.getMetadata("lastArrowDamageEvent").get(0).asLong();
                if (System.currentTimeMillis() - last <= minDelayMilliseconds) {
                    return;
                }
            }
            double duraDamage = 1.0;
            final double dist = player.getLocation().distanceSquared(playerHit.getLocation());
            if (dist >= 100.0) {
                ++duraDamage;
            }
            if (dist >= 400.0) {
                ++duraDamage;
            }
            if (dist >= 1024.0) {
                ++duraDamage;
            }
            final Player pTarget = (Player)playerHit;
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.HELMET, -1);
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.CHESTPLATE, -1);
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.LEGGINGS, -1);
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.BOOTS, -1);
            ParticleEffect.sendToLocation(ParticleEffect.MAGIC_CRIT, playerHit.getLocation(), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.45f, 30);
            ((Player)player).playSound(playerHit.getLocation(), Sound.ANVIL_BREAK, 0.3f, 0.8f);
        }
    }
}
