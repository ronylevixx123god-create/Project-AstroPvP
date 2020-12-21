/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.coreprotect.model.Config
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import net.coreprotect.model.Config;
import org.arkhamnetwork.Arkkit.patches.CommandCOConfig;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class CoreProtectConfig
extends ArkPlugin {
    public CoreProtectConfig(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandCOConfig("coconfig", "/<command>", "Sets in memory config values for CoreProtect!"));
        Config.config.put("natural-break", 0);
        Config.config.put("block-break", 0);
        Bukkit.getLogger().info("[CoreProtectConfig] Set natural-break=0, block-break=0");
    }

    @Override
    public void disable() {
        Config.config.put("natural-break", 1);
        Config.config.put("block-break", 1);
        Bukkit.getLogger().info("[CoreProtectConfig] Set natural-break=1, block-break=1");
    }
}

