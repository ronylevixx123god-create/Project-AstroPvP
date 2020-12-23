/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

final class NoWhitelistTask
        extends BukkitRunnable {
    NoWhitelistTask() {
    }

    public void run() {
        if (Bukkit.hasWhitelist()) {
            Bukkit.setWhitelist(false);
        }
    }
}

