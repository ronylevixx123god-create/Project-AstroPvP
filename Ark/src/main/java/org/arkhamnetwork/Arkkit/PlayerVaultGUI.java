/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit;

import org.arkhamnetwork.Arkkit.CommandVaultName;
import org.arkhamnetwork.Arkkit.PlayerVaultGUIManager;
import org.arkhamnetwork.Arkkit.PlayerVaultListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class PlayerVaultGUI
extends ArkPlugin {
    private PlayerVaultGUIManager manager;

    public PlayerVaultGUI(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.manager = new PlayerVaultGUIManager();
        this.manager.loadCustomVaults();
        this.registerListener(new PlayerVaultListener(this.manager));
        this.registerCommand(new CommandVaultName(this.manager));
    }

    @Override
    public void disable() {
        this.manager.saveCustomVaults();
        this.unregisterListeners();
        this.unregisterCommands();
    }
}

