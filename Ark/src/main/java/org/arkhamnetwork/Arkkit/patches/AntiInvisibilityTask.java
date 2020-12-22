/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicclasses.struct.HCFKits
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Player
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

final class AntiInvisibilityTask
extends BukkitRunnable {
    AntiInvisibilityTask() {
    }

    public void run() {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.isOp() || pl.getGameMode() != GameMode.SURVIVAL || !pl.hasPotionEffect(PotionEffectType.INVISIBILITY)) continue;
            pl.removePotionEffect(PotionEffectType.INVISIBILITY);
            pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "Invisibility potions cannot be used on this server!");
        }
    }

    final int getPoisonEffectDuration(Player p) {
        if (p.hasPotionEffect(PotionEffectType.POISON)) {
            for (PotionEffect pe : p.getActivePotionEffects()) {
                if (pe.getType() != PotionEffectType.POISON) continue;
                return pe.getDuration();
            }
        }
        return 0;
    }
}

