package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

public class HeroicHex extends HeroicCustomEnchantment
{
    public HeroicHex() {
        super("Bewitched Hex", "Hex");
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player)) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.04 * level;
        if (random < chance && (!playerHit.hasMetadata("hexEnchantExpire") || playerHit.getMetadata("hexEnchantExpire").get(0).asLong() < System.currentTimeMillis())) {
            playerHit.setMetadata("hexEnchantExpire", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis() + (2 + level / 2) * 1000));
            playerHit.setMetadata("hexEnchantLevel", new FixedMetadataValue((Plugin)Enchantments.getInstance(), level));
            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, playerHit.getEyeLocation(), 0.6f, 20);
            ((Player)playerHit).sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* HEX DEBUFF [" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + (2 + level / 2) * 2 + "s" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "] *");
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable() {
                @Override
                public void run() {
                    ((Player)playerHit).sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "* HEX OFF *");
                }
            }, (2 + level / 2) * 2 * 20L);
        }
    }

    public static boolean isHexed(final LivingEntity e) {
        return e.hasMetadata("hexEnchantExpire") && e.getMetadata("hexEnchantExpire").get(0).asLong() > System.currentTimeMillis();
    }

    public static int getHexLevel(final LivingEntity e) {
        if (!isHexed(e)) {
            return 0;
        }
        return e.getMetadata("hexEnchantLevel").get(0).asInt();
    }
}
