/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerToggleSneakEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.Random;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class ValentinesDayListener
implements Listener {
    boolean is_hub = Bukkit.getPluginManager().isPluginEnabled("CosmicHub");

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
        if (e.getPlayer().hasMetadata("sneak_love") && System.currentTimeMillis() - ((MetadataValue)e.getPlayer().getMetadata("sneak_love").get(0)).asLong() <= 2500L) {
            return;
        }
        e.getPlayer().setMetadata("sneak_love", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
        Player pl = e.getPlayer();
        ParticleEffect.sendToLocation(ParticleEffect.HEART, pl.getLocation().add(0.0, 1.75, 0.0), 0.0f, 0.0f, 0.0f, 0.001f, 3);
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (this.is_hub) {
            Player pl = e.getPlayer();
            Random r = new Random();
            ParticleEffect.sendToLocation(ParticleEffect.HEART, pl.getLocation().add(0.0, 2.0, 0.0), (float)(r.nextBoolean() ? Math.random() : -Math.random()), (float)(r.nextBoolean() ? Math.random() : -Math.random()), (float)(r.nextBoolean() ? Math.random() : -Math.random()), 0.0025f, 8);
        }
    }
}

