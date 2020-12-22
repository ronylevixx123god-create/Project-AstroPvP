/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerPortalEvent
 */
package me.ifamasssxd.cosmicbosses.listeners;

import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class DungeonBossListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerPortal(PlayerPortalEvent event) {
        Block portalBlock = event.getPlayer().getLocation().getBlock();
        if (portalBlock.hasMetadata("temporalRift")) {
            event.setCancelled(true);
            event.useTravelAgent(false);
            event.setTo(null);
            if (event.getPlayer().isDead() || BossUtils.isInGodMode(event.getPlayer())) {
                return;
            }
            if (!event.getPlayer().getGameMode().equals((Object)GameMode.CREATIVE)) {
                event.getPlayer().setHealth(0.0);
            }
            event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.WITHER_SPAWN, 3.0f, 1.4f);
        }
    }
}

