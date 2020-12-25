/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicbosses.listeners;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class McmmoListener
implements Listener {
    @EventHandler
    public void onXPGain(McMMOPlayerXpGainEvent e) {
        if (e.getPlayer().hasMetadata("no_exp_gain")) {
            e.setCancelled(true);
            e.setRawXpGained(0.0f);
            e.getPlayer().removeMetadata("no_exp_gain", CosmicBosses.getInstance());
        }
    }
}

