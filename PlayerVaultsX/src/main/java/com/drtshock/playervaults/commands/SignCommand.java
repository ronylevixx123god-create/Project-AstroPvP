package com.drtshock.playervaults.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import com.drtshock.playervaults.translations.*;
import com.drtshock.playervaults.*;

public class SignCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (commandSender.hasPermission("playervaults.signs.set")) {
            if (commandSender instanceof Player) {
                if (array.length == 1) {
                    int int1;
                    try {
                        int1 = Integer.parseInt(array[0]);
                    }
                    catch (NumberFormatException ex) {
                        commandSender.sendMessage(Lang.TITLE.toString() + Lang.MUST_BE_NUMBER);
                        commandSender.sendMessage(Lang.TITLE.toString() + "Usage: /" + s + " <owner> <#>");
                        return true;
                    }
                    PlayerVaults.getInstance().getSetSign().put(commandSender.getName(), new SignSetInfo(int1));
                    commandSender.sendMessage(Lang.TITLE.toString() + Lang.CLICK_A_SIGN);
                }
                else if (array.length >= 2) {
                    int int2;
                    try {
                        int2 = Integer.parseInt(array[1]);
                    }
                    catch (NumberFormatException ex2) {
                        commandSender.sendMessage(Lang.TITLE.toString() + Lang.MUST_BE_NUMBER);
                        commandSender.sendMessage(Lang.TITLE.toString() + "Usage: /" + s + " <owner> <#>");
                        return true;
                    }
                    PlayerVaults.getInstance().getSetSign().put(commandSender.getName(), new SignSetInfo(array[0].toLowerCase(), int2));
                    commandSender.sendMessage(Lang.TITLE.toString() + Lang.CLICK_A_SIGN);
                }
                else {
                    commandSender.sendMessage(Lang.TITLE.toString() + Lang.INVALID_ARGS);
                }
            }
            else {
                commandSender.sendMessage(Lang.TITLE.toString() + Lang.PLAYER_ONLY);
            }
        }
        else {
            commandSender.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
        }
        return true;
    }
}
