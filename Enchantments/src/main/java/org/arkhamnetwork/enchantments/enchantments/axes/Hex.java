/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Hex
extends CustomEnchantment {
    public Hex() {
        super("Hex", GeneralUtil.axe, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener)new HexListener(), (Plugin)Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity player, final LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double chance;
        if (!(playerHit instanceof Player)) {
            return;
        }
        double random = Math.random();
        if (random < (chance = 0.02 * (double)level) && (!playerHit.hasMetadata("hexEnchantExpire") || ((MetadataValue)playerHit.getMetadata("hexEnchantExpire").get(0)).asLong() < System.currentTimeMillis())) {
            playerHit.setMetadata("hexEnchantExpire", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(System.currentTimeMillis() + (long)((2 + level / 2) * 1000))));
            playerHit.setMetadata("hexEnchantLevel", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            try {
                ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), playerHit.getEyeLocation(), 0.6f, 0.6f, 0.6f, 0.1f, 20);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            ((Player)playerHit).sendMessage((Object)ChatColor.DARK_PURPLE + "" + (Object)ChatColor.BOLD + "* HEX DEBUFF [" + (Object)ChatColor.LIGHT_PURPLE + (Object)ChatColor.BOLD + (2 + level / 2) + "s" + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + "] *");
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable(){

                @Override
                public void run() {
                    ((Player)playerHit).sendMessage((Object)ChatColor.LIGHT_PURPLE + "" + (Object)ChatColor.BOLD + "* HEX OFF *");
                }
            }, (long)(2 + level / 2) * 20L);
        }
    }

    public static boolean isHexed(LivingEntity e) {
        return e.hasMetadata("hexEnchantExpire") && ((MetadataValue)e.getMetadata("hexEnchantExpire").get(0)).asLong() > System.currentTimeMillis();
    }

    public static int getHexLevel(LivingEntity e) {
        if (!Hex.isHexed(e)) {
            return 0;
        }
        return ((MetadataValue)e.getMetadata("hexEnchantLevel").get(0)).asInt();
    }
}

