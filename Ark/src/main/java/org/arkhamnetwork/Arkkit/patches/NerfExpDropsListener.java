/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerExpChangeEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.NerfExpDrops;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

final class NerfExpDropsListener
implements Listener {
    private NerfExpDrops drops;

    public NerfExpDropsListener(NerfExpDrops drops) {
        this.drops = drops;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerExpChange(PlayerExpChangeEvent e) {
        if (e.getAmount() > 0) {
            e.setAmount((int)Math.round((double)e.getAmount() * this.getPlayerXpRate(e.getPlayer())));
        }
    }

    private double getPlayerXpRate(Player p) {
        double bonusXp = 0.0;
        if (p.hasPermission("arkkit.extra.xp.25")) {
            bonusXp = 0.25;
        } else if (p.hasPermission("arkkit.extra.xp.20")) {
            bonusXp = 0.2;
        } else if (p.hasPermission("arkkit.extra.xp.15")) {
            bonusXp = 0.15;
        } else if (p.hasPermission("arkkit.extra.xp.10")) {
            bonusXp = 0.1;
        } else if (p.hasPermission("arkkit.extra.xp.5")) {
            bonusXp = 0.05;
        }
        return this.drops.expRate + bonusXp;
    }
}

