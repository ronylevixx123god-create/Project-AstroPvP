/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.List;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.CosmicClientHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

final class CommandClientReward
extends AbstractCommand {
    public CommandClientReward(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("xpmod")) {
            double d;
            CosmicClientHelper.extraXPMod = d = Double.parseDouble(args[1]);
            Arkkit.get().getConfig().set("settings.cosmicClientHelper.extraXPMod", (Object)CosmicClientHelper.extraXPMod);
            sender.sendMessage("[Arkkit (CosmicClientHelper)] Set extraXPMod => " + d);
            return true;
        }
        return true;
    }
}

