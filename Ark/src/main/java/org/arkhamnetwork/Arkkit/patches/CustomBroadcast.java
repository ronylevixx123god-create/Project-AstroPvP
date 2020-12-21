/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.Essentials;
import org.arkhamnetwork.Arkkit.patches.CommandBroadcast;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

public class CustomBroadcast
extends ArkPlugin {
    protected static String broadcast_prefix = "";

    public CustomBroadcast(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        broadcast_prefix = this.getBukkitPlugin().getConfig().getString("language.custom_broadcast.prefix");
        if (broadcast_prefix == null) {
            broadcast_prefix = "&a[Broadcast]&r";
            this.getBukkitPlugin().getConfig().set("language.custom_broadcast.prefix", (Object)broadcast_prefix);
        }
        broadcast_prefix = ChatColor.translateAlternateColorCodes((char)'&', (String)broadcast_prefix);
        if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
            Bukkit.getLogger().info("Unregistering Essentials's /broadcast...");
            CommandUtils.unRegisterBukkitCommand(Bukkit.getPluginManager().getPlugin("Essentials"), (Command)((Essentials)Bukkit.getPluginManager().getPlugin("Essentials")).getCommand("broadcast"));
        }
        this.registerCommand(new CommandBroadcast("broadcast", "/<command>", "Send a server-wide message."));
    }

    @Override
    public void disable() {
    }
}

