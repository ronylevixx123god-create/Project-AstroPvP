/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class NoArrowBounce
        extends ArkPlugin {
    public NoArrowBounce(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new NoArrowBounceListener());
    }

    @Override
    public void disable() {
    }
}

