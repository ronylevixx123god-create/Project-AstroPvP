package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class Obliterate extends CustomEnchantment {
    public Obliterate() {
        super("Obliterate", GeneralUtil.weapons, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        final double random = Math.random();
        final double chance = 0.1 * enchantLevel;
        if (target.getWorld().getEnvironment() == World.Environment.THE_END) {
            return;
        }
        final boolean inKoth = GeneralUtil.isKOTHWorld(target.getWorld());
        if (!target.hasMetadata("in_duel") && random < chance) {
            final double d_enchantLevel = enchantLevel;
            ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, target.getLocation().add(0.0, 1.0, 0.0), 1.0f, 3);
            de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayer((Player) target,target.getLocation(), 0,1,0,0.8f,3);
            if (inKoth) {
                return;
            }
            GeneralUtil.pushAwayEntity(user, (Entity) target, 1.8 + d_enchantLevel * 0.5);
        }
    }

    private void pushAwayEntity(final LivingEntity center, final Entity entity, final double speed) {
        final Vector unitVector = entity.getLocation().toVector().subtract(center.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
