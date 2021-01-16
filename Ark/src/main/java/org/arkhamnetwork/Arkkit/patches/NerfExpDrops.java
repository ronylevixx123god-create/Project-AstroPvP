package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class NerfExpDrops
        extends ArkPlugin {
    protected double expRate;

    public NerfExpDrops(Plugin p) {
        super(p);
        this.expRate = p.getConfig().getDouble("settings.nerf_exp_drops.expRate");
    }

    @Override
    public void enable() {
        this.registerListener(new NerfExpDropsListener(this));
    }

    @Override
    public void disable() {
    }
}

