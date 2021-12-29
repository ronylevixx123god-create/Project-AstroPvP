/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Corrupt
extends CustomEnchantment {
    public Corrupt() {
        super("Corrupt", GeneralUtil.axe, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyEffect(LivingEntity player, final LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double chance;
        if (!(playerHit instanceof Player)) {
            return;
        }
        double random = Math.random();
        if (random < (chance = 5.0 + 0.02 * (double)level) && (!playerHit.hasMetadata("corruptEnchantExpire") || ((MetadataValue)playerHit.getMetadata("corruptEnchantExpire").get(0)).asLong() < System.currentTimeMillis())) {
            playerHit.setMetadata("corruptEnchantExpire", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(System.currentTimeMillis() + (long)(level * 2 * 1000))));
            playerHit.setMetadata("corruptEnchantLevel", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            try {
                ParticleEffect.PORTAL.sendToPlayers(Bukkit.getOnlinePlayers(), playerHit.getEyeLocation(), 0.6f, 0.6f, 0.6f, 0.1f, 20);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (EnchantmentAPI.itemHasEnchantment((ItemStack)((Player)playerHit).getItemInHand(), (String)"Inversion")) {
                return;
            }
            if (playerHit.hasMetadata("corruptTaskID")) {
                return;
            }
            int damageAmount = level >= 3 ? 2 : 1;
            int damageInterval = level;
            final int taskID = new CorruptDamageTask((Player)playerHit, damageInterval, damageAmount).runTaskTimer((Plugin)Enchantments.getInstance(), 0L, damageInterval * 20).getTaskId();
            Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)Enchantments.getInstance(), new Runnable(){

                @Override
                public void run() {
                    Bukkit.getScheduler().cancelTask(taskID);
                    Bukkit.getScheduler().runTask((Plugin)Enchantments.getInstance(), () -> playerHit.removeMetadata("corruptTaskID", (Plugin)Enchantments.getInstance()));
                }
            }, (long)Math.pow(level, 2.0) * 20L);
            playerHit.setMetadata("corruptTaskID", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)taskID));
        }
    }

    public static boolean isCorrupted(LivingEntity e) {
        return e.hasMetadata("corruptEnchantExpire") && ((MetadataValue)e.getMetadata("corruptEnchantExpire").get(0)).asLong() > System.currentTimeMillis();
    }

    public static int getCorruptLevel(LivingEntity e) {
        if (!Corrupt.isCorrupted(e)) {
            return 0;
        }
        return ((MetadataValue)e.getMetadata("corruptEnchantLevel").get(0)).asInt();
    }
}

