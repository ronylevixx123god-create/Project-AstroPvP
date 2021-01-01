/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 */
package com.conaxgames.factions.utils.listeners;

import java.util.ArrayList;

import com.massivecraft.factions.P;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

public class InvalidDeathFix
implements Listener {
    @EventHandler(priority=EventPriority.LOW)
    public void onEntityRegainHealth(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player && (((Player)e.getEntity()).getHealth() <= 0.0 || this.isPlayerDead((Player)e.getEntity()))) {
            e.setAmount(0.0);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().hasMetadata("is_dead")) {
            return;
        }
        e.getEntity().setMetadata("is_dead", (MetadataValue)new FixedMetadataValue(P.getP(), (Object)System.currentTimeMillis()));
        for (PotionEffect pe : (e.getEntity().getActivePotionEffects())) {
            e.getEntity().removePotionEffect(pe.getType());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        e.getPlayer().removeMetadata("is_dead", P.getP());
    }

    private boolean isPlayerDead(Player pl) {
        return pl.hasMetadata("is_dead");
    }
}

