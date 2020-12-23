/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

final class CommandVUnban
        extends AbstractCommand {
    public CommandVUnban(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("/vunban <player>");
            return true;
        }
        final String playerName = args[0];
        Bukkit.getScheduler().runTaskAsynchronously(Arkkit.get(), new Runnable() {

            @Override
            public void run() {
                OfflinePlayer op = Bukkit.getOfflinePlayer(playerName);
                if (Bukkit.getBannedPlayers().contains(op)) {
                    Bukkit.getBannedPlayers().remove(op);
                    sender.sendMessage(playerName + " has been removed from the bukkit/vanilla banned-players.json list.");
                } else {
                    sender.sendMessage(playerName + " is not currently on the bukkit/vanilla banned-players.json list.");
                }
            }
        });
        return true;
    }
}

