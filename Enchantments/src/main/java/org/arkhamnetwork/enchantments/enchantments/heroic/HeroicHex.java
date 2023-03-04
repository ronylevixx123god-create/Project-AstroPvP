package org.arkhamnetwork.enchantments.enchantments.heroic;

import de.inventivegames.particle.ParticleEffect;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

import java.util.Random;

public class HeroicHex
        extends HeroicCustomEnchantment {
    public HeroicHex() {
        super("Bewitched Hex", "Hex");
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyEffect(LivingEntity player, final LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double chance;
        if (!(playerHit instanceof Player)) {
            return;
        }
        double random = Math.random();
        if (random < (chance = 0.04 * (double)level) && (!playerHit.hasMetadata("hexEnchantExpire") || playerHit.getMetadata("hexEnchantExpire").get(0).asLong() < System.currentTimeMillis())) {
            playerHit.setMetadata("hexEnchantExpire", new FixedMetadataValue((Plugin)((Object)Enchantments.getInstance()), System.currentTimeMillis() + (long)((2 + level / 2) * 1000)));
            playerHit.setMetadata("hexEnchantLevel", new FixedMetadataValue((Plugin)((Object)Enchantments.getInstance()), level));
            ((Player)playerHit).sendMessage((Object)((Object)ChatColor.DARK_PURPLE) + "" + (Object)((Object)ChatColor.BOLD) + "* HEX DEBUFF [" + (Object)((Object)ChatColor.LIGHT_PURPLE) + (Object)((Object)ChatColor.BOLD) + (2 + level / 2) * 2 + "s" + (Object)((Object)ChatColor.DARK_PURPLE) + (Object)((Object)ChatColor.BOLD) + "] *");
            Bukkit.getScheduler().runTaskLater((Plugin)((Object)Enchantments.getInstance()), new Runnable(){

                @Override
                public void run() {
                    ((Player)playerHit).sendMessage((Object)((Object)ChatColor.LIGHT_PURPLE) + "" + (Object)((Object)ChatColor.BOLD) + "* HEX OFF *");
                }
            }, (long)((2 + level / 2) * 2) * 20L);
        }
    }

    public static boolean isHexed(LivingEntity e) {
        return e.hasMetadata("hexEnchantExpire") && e.getMetadata("hexEnchantExpire").get(0).asLong() > System.currentTimeMillis();
    }

    public static int getHexLevel(LivingEntity e) {
        if (!HeroicHex.isHexed(e)) {
            return 0;
        }
        return e.getMetadata("hexEnchantLevel").get(0).asInt();
    }
}
