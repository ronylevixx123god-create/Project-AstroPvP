/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

final class HubCommandListener
implements Listener {
    HubCommandListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        String s = e.getMessage().toLowerCase();
        if ((s.equals("/hub") || s.equals("/lobby") || s.equals("/quit") || s.equals("exit") || s.equals("leave")) && e.isCancelled() && !CombatLog.inCombat(e.getPlayer())) {
            Bukkit.getLogger().info("(Arkkit [HubCommand]) Removing event cancellation for " + s + " from " + e.getPlayer().getName() + "!");
            e.setCancelled(false);
        }
    }
}

