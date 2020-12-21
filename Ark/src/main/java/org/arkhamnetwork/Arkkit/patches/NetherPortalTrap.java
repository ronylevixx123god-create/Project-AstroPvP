/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.NetherPortalTrapListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class NetherPortalTrap
extends ArkPlugin {
    public NetherPortalTrap(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerTask(new NetherPortalTrapListener().runTaskTimer((Plugin)Arkkit.get(), 200L, 200L), NetherPortalTrap.class);
    }

    @Override
    public void disable() {
    }
}

