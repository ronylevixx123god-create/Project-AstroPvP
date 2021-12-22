package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.listeners.VanillaDamageListener;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralCooldown;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class DoubleStrike extends CustomEnchantment {
    public DoubleStrike() {
        super("Double Strike", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 10.0D;
        this.interval = 10.0D;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random();
        double chance = 0.015D * level;
        if (random < chance) {
            final double fdmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            Player pp = (Player) e.getDamager();

            Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), new Runnable() {
                public void run() {
                    if (!playerHit.isDead() && playerHit.getHealth() > 0.0D) {
                        playerHit.setMetadata("doubleStriked", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), Long.valueOf(System.currentTimeMillis())));
                        playerHit.damage(fdmg, (Entity) player);
                        pp.sendMessage(CC.translate("&6&l** DOUBLE STRIKE **"));
                        GeneralCooldown.enterCooldown(pp);
                        if (playerHit instanceof Player) {
                            DamageIndicatorUtils.sendDamageIndicator((Player) playerHit, CC.RedB + "* DOUBLE STRIKE *", "DOUBLE_STRIKE");
                        }
                    }
                }
            }, 2L);

            try {
                de.inventivegames.particle.ParticleEffect.RED_DUST.sendToPlayers(Bukkit.getOnlinePlayers(),playerHit.getLocation(), (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5F, 20);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }


}
