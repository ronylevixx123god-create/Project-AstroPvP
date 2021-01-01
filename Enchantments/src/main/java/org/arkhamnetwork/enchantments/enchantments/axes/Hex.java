package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.*;

public class Hex extends CustomEnchantment
{
    public Hex() {
        super("Hex", GeneralUtil.axe, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener)new HexListener(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player)) {
            return;
        }
        if (isHexed(playerHit))
            return;
        final double random = Math.random();
        final double chance = 0.02 * level;
        if (random < chance && (!playerHit.hasMetadata("hexEnchantExpire") || playerHit.getMetadata("hexEnchantExpire").get(0).asLong() < System.currentTimeMillis())) {
            playerHit.setMetadata("hexEnchantExpire", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(System.currentTimeMillis() + (2 + level / 2) * 1000)));
            playerHit.setMetadata("hexEnchantLevel", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, playerHit.getEyeLocation(), 0.6f, 20);
            ((Player)playerHit).sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* HEX DEBUFF [" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + (2 + level / 2) + "s" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "] *");
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    ((Player)playerHit).sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "* HEX OFF *");
                }
            }, (2 + level / 2) * 20L);
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
