/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class PvPSanity
        extends ArkPlugin {
    public PvPSanity(Plugin p) {
        super(p);
    }

    @Override
    public void enable() throws IOException {
        super.enable();
        this.registerListener(new PvPSanityListener());
    }

    @Override
    public void disable() throws IOException {
        super.disable();
    }
}

