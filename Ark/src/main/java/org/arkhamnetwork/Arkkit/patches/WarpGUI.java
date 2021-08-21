package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class WarpGUI extends ArkPlugin {
    private WarpGUIManager warpManager = new WarpGUIManager();

    public WarpGUI(Plugin p) {
        super(p);
    }

    public void enable() {
        registerListener(new WarpGUIListener(this.warpManager));
        this.warpManager.loadHomes();
    }

    public void disable() {
        this.warpManager.saveWarps();
        unregisterListeners();
    }
}
