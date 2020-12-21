/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  de.dustplanet.util.SilkUtil
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import de.dustplanet.util.SilkUtil;
import org.arkhamnetwork.Arkkit.patches.NoWaterProtSpawnersListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class NoWaterProtSpawners
extends ArkPlugin {
    protected static SilkUtil su = null;

    public NoWaterProtSpawners(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        su = SilkUtil.hookIntoSilkSpanwers();
        this.registerListener(new NoWaterProtSpawnersListener());
    }

    @Override
    public void disable() {
    }
}

