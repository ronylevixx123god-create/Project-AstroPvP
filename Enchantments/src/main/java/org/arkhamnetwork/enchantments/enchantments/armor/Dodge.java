package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import me.astropvp.astroparticles.utils.ParticleUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Dodge extends CustomEnchantment {
    public Dodge() {
        super("Dodge", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 5.0;
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            double chance = 0.025 * level;
            final boolean sneaking = ((Player) player).isSneaking();
            if (sneaking) {
                chance += 0.15;
            }
            if (Math.random() < chance) {
                e.setCancelled(true);
                ((Player) player).sendMessage(ChatColor.YELLOW + "*DODGE*");
                if (ParticleUtils.isPlayerIndicating(player.getKiller())) {
                    de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation(), 0, 1, 0, 0.2f, 10);
                }
                player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.75f);
            }
        }
    }
}
