package org.arkhamnetwork.enchantments.enchantments.armor;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.potion.*;
import org.arkhamnetwork.enchantments.libs.*;
import com.rit.sucy.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.*;
import java.util.*;

public class Drunk extends CustomEnchantment
{
    public Drunk() {
        super("Drunk", GeneralUtil.helmets, 5);
        this.max = 4;
        this.base = 15.0;
        this.interval = 6.0;
    }
    
    public void applyEquipEffect(final Player player, final int level) {
        if (player.hasPotionEffect(PotionEffectType.SLOW) && this.getPotionEffectLevel(player, PotionEffectType.SLOW) <= level / 2) {
            player.removePotionEffect(PotionEffectType.SLOW);
        }
        if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING) && this.getPotionEffectLevel(player, PotionEffectType.SLOW_DIGGING) <= level / 2) {
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }
        PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(this, level), new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, level / 2));
        PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(this, level), new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, Math.max(level - 2, 0)));
        PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(this, level), new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, level / 2));
    }
    
    public void applyUnequipEffect(final Player player, final int level) {
        PlayerPotionEffects.untrackPotionEffect(player, new SpecificCustomEnchantment(EnchantmentAPI.getEnchantment("Drunk"), level));
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double blackoutChance = level * 0.01;
        if (Math.random() < blackoutChance) {
            final Player p = (Player)player;
            p.sendMessage(ChatColor.YELLOW + "You're starting to feel very dizzy...");
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, level * 40, Math.max(0, level - 3)));
        }
    }
    
    private int getPotionEffectLevel(final Player p, final PotionEffectType pet) {
        for (final PotionEffect pe : p.getActivePotionEffects()) {
            if (pe.getType() == pet) {
                return pe.getAmplifier();
            }
        }
        return 0;
    }
}
