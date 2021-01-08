package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

final class TaperSlotsTask
        extends BukkitRunnable {
    private final int maxSlots = Arkkit.get().getConfig().getInt("settings.taperSlots.maxSlots");

    public TaperSlotsTask() {
        DynamicMaxPlayers.setMaxPlayers(Math.min(100, this.maxSlots));
        Bukkit.getLogger().info("[Arkkit (TaperSlots)] TaperEntryTask loaded, starting at 100 slots, incrementing to " + this.maxSlots + " slots...");
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

