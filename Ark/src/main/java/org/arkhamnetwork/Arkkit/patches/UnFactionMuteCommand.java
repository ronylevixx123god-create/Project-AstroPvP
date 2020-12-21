/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.ExtraMute;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class UnFactionMuteCommand
extends AbstractCommand {
    private ExtraMute instance;

    public UnFactionMuteCommand(ExtraMute mute, String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
        this.instance = mute;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp() && !sender.hasPermission("facmute.unmute")) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage((Object)ChatColor.RED + "Invalid Usage: /unfacmute <faction>");
            return true;
        }
        List fplayerIds = FactionUtils.getFPlayerIdsByTag((String)args[0]);
        if (fplayerIds == null) {
            sender.sendMessage((Object)ChatColor.RED + "Invalid faction given.");
            return true;
        }
        for (String id : fplayerIds) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("unmute " + id));
        }
        sender.sendMessage((Object)ChatColor.GREEN + "Ran unmute command on all faction members..!");
        return false;
    }
}

