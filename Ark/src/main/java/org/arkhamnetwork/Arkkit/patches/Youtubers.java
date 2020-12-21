/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CommandBless;
import org.arkhamnetwork.Arkkit.patches.CommandTogglePlayer;
import org.arkhamnetwork.Arkkit.patches.TogglePlayerListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class Youtubers
extends ArkPlugin {
    protected static List<String> youtubers;

    public Youtubers(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        youtubers = new ArrayList<String>(Arrays.asList("Woofless", "TBNRfrags", "Lachlan", "Vikkstar123", "iFamasssxD"));
        this.registerCommand(new CommandTogglePlayer("toggleplayer", "/<command> [args]", "Toggles visibilty of players.", new ArrayList<String>(Arrays.asList("hideplayers", "showplayers", "ghost"))));
        if (Arkkit.get().getConfig().getBoolean("settings.youtubers.bless", true)) {
            this.registerCommand(new CommandBless("bless", "/<command> [args]", "Bless command.", new ArrayList<String>(Arrays.asList("blessme"))));
        }
        this.registerListener(new TogglePlayerListener());
    }

    @Override
    public void disable() {
    }
}

