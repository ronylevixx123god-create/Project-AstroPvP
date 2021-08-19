package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import lombok.SneakyThrows;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HeroicDodge extends HeroicCustomEnchantment {
    public HeroicDodge() {
        super("Ethereal Dodge", "Dodge", GeneralUtil.boots, 2);
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            double chance = 0.02 * level;
            final boolean sneaking = ((Player) player).isSneaking();
            if (sneaking) {
                chance += 0.2;
            }
            if (Math.random() < chance) {
                if (attacker == null) return;
                if (AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.HALLOWEEN_AXE)) return;

                e.setCancelled(true);
                ((Player) player).sendMessage(ChatColor.LIGHT_PURPLE + "*DODGE*");
                try {
                    de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(),player.getLocation(), 0,1, 0,0.2f,10);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.75f);
                final double chanceForSpeed = 5 + (level - 1) * 2;
                if (Math.random() < chanceForSpeed * 0.01) {
                    PlayerPotionEffects.trackPotionEffect((Player) player, new SpecificCustomEnchantment(this, level), new PotionEffect(PotionEffectType.SPEED, 60, Math.max(2, 2 + level / 2)));
                }
            }
        } else if (e != null && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (player.getFallDistance() > 5.0f) {
                ((Player) player).sendMessage(ChatColor.YELLOW + "* FALL DODGED *");
            }
            e.setDamage(0.0);
            e.setCancelled(true);
        }
    }
}
