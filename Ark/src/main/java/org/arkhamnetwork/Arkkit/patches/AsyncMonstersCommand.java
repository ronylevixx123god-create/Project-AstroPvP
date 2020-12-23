/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class AsyncMonstersCommand
        extends AbstractCommand {
    public AsyncMonstersCommand(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/asyncmonsters [creeper/setmax/setlavapercent] [value]");
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("creeper")) {
            AsyncMonsters.stackCreepers = !AsyncMonsters.stackCreepers;
            sender.sendMessage(CC.Red + "Stacking Creepers: " + AsyncMonsters.stackCreepers);
            return true;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("setmax")) {
                int max;
                AsyncMonsters.MAX_KILL_STACK = max = Integer.parseInt(args[1]);
                sender.sendMessage("MAX_KILL_STACK = " + max);
                return true;
            }
            if (args[0].equalsIgnoreCase("setlavapercent")) {
                double percent;
                AsyncMonsters.LAVA_KILL_PERCENT = percent = Double.parseDouble(args[1]);
                sender.sendMessage("LAVA_KILL_PERCENT = " + percent);
                return true;
            }
        }
        return false;
    }
}

