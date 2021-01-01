package org.arkhamnetwork.enchantments.enchantments.bow;

import org.arkhamnetwork.enchantments.*;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.cosmicpvp.cosmicutils.utils.*;

public class Sniper extends CustomEnchantment
{
    public Sniper() {
        super("Sniper", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 8.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
            @Override
            public void run() {
                Sniper.this.heroicVersion = EnchantmentAPI.getEnchantment("Lethal Sniper");
            }
        }, 2L);
    }
    
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
            return;
        }
        final Projectile proj = (Projectile)event.getDamager();
        if (!(proj.getShooter() instanceof Player)) {
            return;
        }
        final Entity victima = (Entity)target;
        final double y = proj.getLocation().getY();
        final double victimay = victima.getLocation().getY();
        final double level = enchantLevel;
        final boolean headshot = y - victimay > 1.9;
        if (headshot && Math.random() < 0.35 + level * 0.075) {
            if (GeneralUtil.isEffectedByRage(target)) {
                return;
            }
            final double multiplier = level * 0.42 + 1.0;
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * multiplier);
            this.playBleedEffect(target.getEyeLocation(), enchantLevel);
            if (target instanceof Player) {
                ((Player)target).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "*** HEADSHOT [+" + multiplier + "x DMG] ***");
                ((Player)target).playSound(target.getLocation(), Sound.HURT_FLESH, 2.0f, 0.3f);
            }
        }
    }
    
    private void playBleedEffect(final Location loc, final int bleedStack) {
        for (int i = Math.max(1, bleedStack / 2); i > 0; --i) {
            WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
        }
    }
}
