/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.AntiScoreboardLeak;
import org.bukkit.scheduler.BukkitRunnable;

final class AntiScoreboardLeakTask
extends BukkitRunnable {
    AntiScoreboardLeakTask() {
    }

    public void run() {
        AntiScoreboardLeak.auditScoreboardList();
    }
}

