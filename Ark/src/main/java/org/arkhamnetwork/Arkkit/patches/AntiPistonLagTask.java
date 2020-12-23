/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.scheduler.BukkitRunnable;

final class AntiPistonLagTask
        extends BukkitRunnable {
    AntiPistonLagTask() {
    }

    public void run() {
        AntiPistonLagListener.warnedChunkCoords.clear();
    }
}

