package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.enchantments.axes.Corrupt;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class Inversion extends CustomEnchantment {
    public Inversion() {
        super("Inversion", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 17.0;
        this.interval = 4.0;
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity entityHit, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final float procChance = (float) (0.05 * level);
        if (Math.random() <= procChance) {
            final Player playerHit = (Player) entityHit;
            final double heal = new Random().nextInt(3) + 1;
            if (heal >= 1.0) {
                if (Corrupt.isCorrupted((LivingEntity) playerHit) && Math.random() < Corrupt.getCorruptLevel((LivingEntity) playerHit) * 0.2f) {
                    playerHit.damage(heal);
                    playerHit.sendMessage(ChatColor.DARK_PURPLE + "* CORRUPTED [" + ChatColor.RED + heal + ChatColor.DARK_PURPLE + " DMG] *");
                    return;
                }
                //GeneralUtil.healPlayer((LivingEntity) playerHit, heal);
                e.setDamage(0.0);
                e.setCancelled(true);
                try {
                    de.inventivegames.particle.ParticleEffect.SPELL.sendToPlayer(playerHit,playerHit.getLocation(), 0,1, 0,1.0f,4);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                playerHit.playSound(playerHit.getLocation(), Sound.PISTON_EXTEND, 0.8f, 2.0f);
            }
        }
    }
}
