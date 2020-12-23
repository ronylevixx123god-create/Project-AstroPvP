/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;
import java.util.TimeZone;

public class PlayerHeads
        extends ArkPlugin {
    protected static Calendar calendar;

    public PlayerHeads(Plugin p) {
        super(p);
        calendar = Calendar.getInstance(TimeZone.getDefault());
    }

    @Override
    public void enable() {
        this.registerListener(new PlayerHeadsListener());
    }

    @Override
    public void disable() {
    }
}

