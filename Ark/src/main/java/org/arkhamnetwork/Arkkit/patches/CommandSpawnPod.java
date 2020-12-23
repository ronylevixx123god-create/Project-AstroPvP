/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

final class CommandSpawnPod
        extends AbstractCommand {
    public CommandSpawnPod(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/spawnpod <playerName>");
            return true;
        }
        String playerName = args[0];
        SpawnPodListener.newPlayers.add(playerName);
        sender.sendMessage("Added " + playerName + " to newPlayers map, they will get a spawn pod on relog.");
        return true;
    }
}

