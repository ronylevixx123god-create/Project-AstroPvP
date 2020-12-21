/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.HashMap;
import org.arkhamnetwork.Arkkit.patches.LavaSpongeListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class LavaSponge
extends ArkPlugin {
    protected static HashMap<Location, Location> spongeMap = new HashMap();

    public LavaSponge(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new LavaSpongeListener());
    }

    @Override
    public void disable() {
    }
}

