/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;

public interface BukkitTask {
    public int getTaskId();

    public Plugin getOwner();

    public boolean isSync();

    public void cancel();
}

