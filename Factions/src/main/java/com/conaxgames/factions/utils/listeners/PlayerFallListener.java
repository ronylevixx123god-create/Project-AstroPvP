/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.me.astropvp.cratekeys.net.astropvp.armorsets.utils.WorldGuardUtils
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.player.PlayerToggleFlightEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.conaxgames.factions.utils.listeners;

import com.massivecraft.factions.P;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class PlayerFallListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            String region = WorldGuardUtils.getRegionName((Location)e.getEntity().getLocation());
            if (e.getEntity().hasMetadata("no_fall_damage") || region.toLowerCase().contains("warzone") || region.toLowerCase().contains("warp")) {
                e.getEntity().removeMetadata("no_fall_damage", P.getP());
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        e.getPlayer().setMetadata("no_fall_damage", (MetadataValue)new FixedMetadataValue(P.getP(), (Object)true));
    }
}

