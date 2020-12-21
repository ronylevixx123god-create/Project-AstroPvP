/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.Arrays;
import org.arkhamnetwork.Arkkit.patches.CommandClientReward;
import org.arkhamnetwork.Arkkit.patches.CosmicClientHelperListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class CosmicClientHelper
extends ArkPlugin {
    public static double extraXPMod = 1.0;

    public CosmicClientHelper(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        extraXPMod = this.getBukkitPlugin().getConfig().getDouble("settings.cosmicClientHelper.extraXPMod");
        this.registerListener(new CosmicClientHelperListener());
        this.registerCommand(new CommandClientReward("clientreward", "/<command>", "Command to set dynamic client rewards.", Arrays.asList("")));
    }

    @Override
    public void disable() {
    }
}

