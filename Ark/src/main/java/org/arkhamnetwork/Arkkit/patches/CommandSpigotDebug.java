/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.spigotmc.SpigotConfig
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.spigotmc.SpigotConfig;

final class CommandSpigotDebug
        extends AbstractCommand {
    public CommandSpigotDebug(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args[0].equals("on")) {
            SpigotConfig.debug = true;
            sender.sendMessage("SpigotConfig.debug = true");
        } else {
            SpigotConfig.debug = false;
            sender.sendMessage("SpigotConfig.debug = false");
        }
        return true;
    }
}

