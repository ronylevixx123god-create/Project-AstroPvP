/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package com.massivecraft.factions.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommandPreProcesS(PlayerCommandPreprocessEvent event) {
        String lower = event.getMessage().toLowerCase();
        if (lower.startsWith("/f banner")) {
            event.setMessage(lower.replace("/f banner", "/fbanner"));
        } else if (lower.startsWith("/f recall") || lower.startsWith("/f assist")) {
            event.setMessage(lower.replace("/f recall", "/frecall").replace("/f assist", "/frecall"));
        } else if (lower.startsWith("/f setbanner")) {
            event.setMessage(lower.replace("/f setbanner", "/fsetbanner"));
        }
    }
}

