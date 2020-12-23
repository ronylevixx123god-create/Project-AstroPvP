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

public class AutoRespawn
        extends ArkPlugin {
    protected static int respawnDelaySeconds = 5;

    public AutoRespawn(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        respawnDelaySeconds = Arkkit.get().getConfig().getInt("settings.autoRespawn.respawnDelaySeconds", 5);
        this.registerListener(new AutoRespawnListener());
    }

    @Override
    public void disable() {
    }
}

