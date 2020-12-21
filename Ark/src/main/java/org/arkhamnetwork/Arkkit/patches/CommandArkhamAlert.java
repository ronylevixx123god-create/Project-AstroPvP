/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.AlertSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandArkhamAlert
extends AbstractCommand {
    public CommandArkhamAlert(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && !((Player)sender).isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("Usage: /gbroadcast <msg>");
            sender.sendMessage("Sends a global message to all servers running Arkkit on this LilyPad cluster.");
            return true;
        }
        String msg = "";
        for (String s : args) {
            msg = msg + s + " ";
        }
        if (msg.endsWith(" ")) {
            msg = msg.substring(0, msg.length() - 1);
        }
        StringBuilder builder = new StringBuilder();
        builder.append(msg);
        AlertSystem.request("ArkhamAlert.message", builder.toString());
        return true;
    }
}

