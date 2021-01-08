/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 */
package com.compilex.CosmicOutposts.outpost.tasks;

import com.compilex.CosmicOutposts.outpost.Outpost;
import com.compilex.CosmicOutposts.utils.WorldGuardUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SanityTask
implements Runnable {
    private Outpost outpost;

    public SanityTask(Outpost outpost) {
        this.outpost = outpost;
    }

    @Override
    public void run() {
        for (Player p : this.outpost.getWorld().getPlayers()) {
            if (p.hasPermission("staff.outpost.godmode") || !WorldGuardUtils.getRegionName(p.getLocation()).contains("defender") || this.outpost.getCapTask().getDefendingFaction() != null && ((FPlayer)FPlayers.i.get((OfflinePlayer)p)).getFaction().getTag().equals(this.outpost.getCapTask().getDefendingFaction().getTag())) continue;
            p.setHealth(Math.max(0.0, p.getHealth() - 2.0));
            p.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + " - 2HP" + (Object)ChatColor.GRAY + " (defender spawn debuff)");
        }
    }
}

