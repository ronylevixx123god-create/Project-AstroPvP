package com.conaxgames.customskulls.commands;

import com.conaxgames.customskulls.CoreSkulls;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClearGrinderBlocksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!sender.isOp()) {
            sender.sendMessage(CC.Red + "No perms.");
            return false;
        }
        CoreSkulls.instance.getGrinderBlocks().clear();
        sender.sendMessage(CC.Green + "Cleared all the cached grinder blocks");
        return true;
    }
}
