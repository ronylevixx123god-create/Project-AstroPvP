/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CompilexServerSync.CompilexServerSync
 *  org.bukkit.Bukkit
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CompilexServerSync.CompilexServerSync;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

final class TaperSlotsTask
        extends BukkitRunnable {
    private final int maxSlots = Arkkit.get().getConfig().getInt("settings.taperSlots.maxSlots");

    public TaperSlotsTask() {
        if (CompilexServerSync.get().isQueue()) {
            DynamicMaxPlayers.setMaxPlayers(this.maxSlots);
        } else {
            DynamicMaxPlayers.setMaxPlayers(Math.min(100, this.maxSlots));
            Bukkit.getLogger().info("[Arkkit (TaperSlots)] TaperEntryTask loaded, starting at 100 slots, incrementing to " + this.maxSlots + " slots...");
        }
    }

    public void run() {
        if (Bukkit.getMaxPlayers() < 100) {
            return;
        }
        int maxPlayers = Bukkit.getMaxPlayers();
        if (maxPlayers >= this.maxSlots) {
            return;
        }
        maxPlayers = Math.min(this.maxSlots, maxPlayers + 50);
        DynamicMaxPlayers.setMaxPlayers(maxPlayers);
    }
}

