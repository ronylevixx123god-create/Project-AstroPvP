/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.coreprotect.model.Config
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import net.coreprotect.model.Config;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

final class CommandCOConfig
extends AbstractCommand {
    public CommandCOConfig(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/coconfig <key> <value>");
            sender.sendMessage("/coconfig get <key>");
            return true;
        }
        String key = args[0];
        String val = args[1];
        if (key.equalsIgnoreCase("get")) {
            if (Config.config.containsKey(val)) {
                sender.sendMessage("CoreProtect config.yml " + key + " = " + Config.config.get(val));
            } else {
                sender.sendMessage("No config entry for key: " + val);
            }
            return true;
        }
        int ival = Integer.parseInt(val);
        Config.config.put(key, ival);
        sender.sendMessage("Set CoreProtect config.yml value of " + key + " = " + ival);
        return true;
    }
}

