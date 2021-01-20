/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

final class PortalRegionListener
        implements Listener {
    PortalRegionListener() {
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e) throws Exception {
        Location to = e.getTo();
        Location from = e.getFrom();
        if (to.getBlockX() == from.getBlockX() && to.getBlockZ() == to.getBlockZ()) {
            return;
        }
        if (CombatLog.inCombat(e.getPlayer())) {
            return;
        }
        String regionName = WorldGuardUtils.getRegionName(to);
        if (regionName.startsWith("portal_")) {
            String warpDestination = regionName.split("_")[1];
            e.getPlayer().chat("/warp " + warpDestination);
            //ParticleEffect.playEffect(ParticleEffect.RED_DUST, e.getPlayer().getLocation().add(0.0, 1.0, 0.0), 0.8f, 25);
            de.inventivegames.particle.ParticleEffect.RED_DUST.sendToPlayer(e.getPlayer(),e.getPlayer().getLocation(), 0,1,0,0.8f,25);

        }
    }
}

