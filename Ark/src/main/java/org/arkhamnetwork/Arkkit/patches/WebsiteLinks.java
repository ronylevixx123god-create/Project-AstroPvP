/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.Arrays;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class WebsiteLinks
extends ArkPlugin {
    public WebsiteLinks(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandForums("forums", "/<command>", "Displays link to official server forums.", new ArrayList<>(Arrays.asList("forum"))));
        this.registerCommand(new CommandReport("report", "/<command>", "Displays link to player report form.", new ArrayList<>(Arrays.asList("reportplayer"))));
    }

    @Override
    public void disable() {
    }
}

