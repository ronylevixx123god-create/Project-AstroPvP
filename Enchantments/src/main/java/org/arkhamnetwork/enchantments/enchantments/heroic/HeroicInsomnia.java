package org.arkhamnetwork.enchantments.enchantments.heroic;

import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.enchantments.axes.Corrupt;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class HeroicInsomnia extends HeroicCustomEnchantment {
    public HeroicInsomnia() {
        super("Nocturnal Insomnia","Insomnia", GeneralUtil.swords, 2);
        this.max = 7;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double random = Math.random();
        final double chance = 0.030 * level;
        final int time = (level > 1) ? 4 : 3;
        if (random < chance) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Math.max(time * 10, 40), Math.min(level - 1, 2)));
            if (level >= 6) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Math.max(time * 10, 40), 1));
            }
            final double multi = 1.0 + level * 0.02;
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * multi);
        }
    }
}
