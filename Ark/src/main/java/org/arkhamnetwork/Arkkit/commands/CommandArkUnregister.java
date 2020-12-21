/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.commands;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandArkUnregister
implements CommandExecutor {
    private Arkkit plugin = null;

    public CommandArkUnregister(Plugin p) {
        this.plugin = (Arkkit)p;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && !((Player)sender).isOp()) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("Usage: /arkunregister <patch>");
            sender.sendMessage("Loaded Patches: [" + ArkPlugins.getLoadedPatches() + "]");
            return true;
        }
        String patch = args[0];
        if (this.plugin.unregisterPatch(patch)) {
            if (sender instanceof Player) {
                sender.sendMessage("Unloaded patches." + patch.toLowerCase());
            }
            this.plugin.getConfig().set("patches." + patch.toLowerCase(), (Object)false);
            return true;
        }
        sender.sendMessage("No patch with surname '" + patch + "' is loaded!");
        return true;
    }
}

