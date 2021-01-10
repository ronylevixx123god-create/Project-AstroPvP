/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitScheduler
 */
package me.ifamasssxd.cosmicmasks.tasks;

import me.ifamasssxd.cosmicmasks.CosmicMasks;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class SpeedMultiMaskFix {
    public static void fixMasks() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask((Plugin)CosmicMasks.get(), () -> {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (!MaskType.hasEquipped(p, "TERMINATOR") && !MaskType.hasEquipped(p, "REINDEER")) continue;
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 3), true);
            }
        }, 80L, 80L);
    }
}

