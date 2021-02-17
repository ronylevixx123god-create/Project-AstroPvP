package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class SpiritLink extends CustomEnchantment {
    public SpiritLink() {
        super("Spirit Link", GeneralUtil.chestplates, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double chance = level * 0.03;
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && Math.random() < chance) {
            final Player playerHurt = (Player) player;
            final double healAmount = Math.min(4.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) / (this.max - (level - 1)) / 4.0);
            final double radius = level * 2;
            for (final Entity ent : player.getNearbyEntities(radius, radius, radius)) {
                if (ent instanceof Player) {
                    final Player playerNear = (Player) ent;
                    if (!this.isAlly(playerHurt, playerNear)) {
                        continue;
                    }
                    //GeneralUtil.healPlayer((LivingEntity) playerNear, healAmount);
                    try {
                        de.inventivegames.particle.ParticleEffect.HEART.sendToPlayers(Bukkit.getOnlinePlayers(), playerNear.getEyeLocation().add(0.0, 0.35, 0.0), 0.0f, (float) Math.random(), 0.0f, 0.05f, 1);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
            }
            try {
                de.inventivegames.particle.ParticleEffect.HEART.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation().add(0.0, 0.5, 0.0), (float) Math.random() / 2.0f, (float) Math.random() / 2.0f, (float) Math.random() / 2.0f, 0.05f, 3);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.35f);
        }
    }

    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
