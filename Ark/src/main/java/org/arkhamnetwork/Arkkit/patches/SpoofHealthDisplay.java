/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.libraryaddict.disguise.DisguiseAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scoreboard.DisplaySlot
 *  org.bukkit.scoreboard.Scoreboard
 */
package org.arkhamnetwork.Arkkit.patches;

import me.libraryaddict.disguise.DisguiseAPI;
import org.arkhamnetwork.Arkkit.patches.PlayerHealth;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

final class SpoofHealthDisplay
extends BukkitRunnable {
    Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();

    SpoofHealthDisplay() {
    }

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Entity disguise;
            if (!PlayerHealth.playerDisguises.containsKey(p.getName()) || !DisguiseAPI.isDisguised((Entity)p) || !((disguise = DisguiseAPI.getDisguise((Entity)p).getEntity()) instanceof Player)) continue;
            String disguiseName = PlayerHealth.playerDisguises.get(p.getName());
            this.s.getObjective(DisplaySlot.BELOW_NAME).getScore(disguiseName).setScore((int)p.getHealth());
        }
    }
}

