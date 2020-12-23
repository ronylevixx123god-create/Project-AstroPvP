/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class SandStackLimiter
        extends ArkPlugin {
    protected static int maxStackSize = 25;

    public SandStackLimiter(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        maxStackSize = Arkkit.get().getConfig().getInt("settings.sand_stack_limiter.maxStackSize");
        this.registerListener(new SandStackLimiterListener());
    }

    @Override
    public void disable() {
    }
}

