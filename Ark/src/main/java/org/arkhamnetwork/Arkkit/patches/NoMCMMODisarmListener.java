/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.events.skills.unarmed.McMMOPlayerDisarmEvent
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 */
package org.arkhamnetwork.Arkkit.patches;

import com.gmail.nossr50.events.skills.unarmed.McMMOPlayerDisarmEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

final class NoMCMMODisarmListener
implements Listener {
    NoMCMMODisarmListener() {
    }

    @EventHandler
    public void onMcMMOPlayerDisarm(McMMOPlayerDisarmEvent e) {
        e.setCancelled(true);
    }
}

