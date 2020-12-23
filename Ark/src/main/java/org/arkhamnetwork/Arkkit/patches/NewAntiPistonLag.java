/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Chunk
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.LongHash;
import org.bukkit.Chunk;
import org.bukkit.plugin.Plugin;

public class NewAntiPistonLag
        extends ArkPlugin {
    public int tickInterval = 20;
    public int maxPistonEventsPerChunk = 100;

    public NewAntiPistonLag(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.tickInterval = Arkkit.get().getConfig().getInt("settings.anti_piston_lag.tickInterval", 20);
        this.maxPistonEventsPerChunk = Arkkit.get().getConfig().getInt("settings.anti_piston_lag.maxPistonEventsPerChunk", 100);
        this.registerListener(new NewAntiPistonLagListener(this));
        this.registerTask(new NewAntiPistonLagTask(this).runTaskTimer(Arkkit.get(), this.tickInterval, this.tickInterval), NewAntiPistonLagTask.class);
    }

    @Override
    public void disable() {
    }

    public long getChunkValue(Chunk c) {
        return LongHash.toLong(c.getX(), c.getZ());
    }

    public String getChunkString(Chunk c) {
        return c.getX() + "," + c.getZ();
    }
}

