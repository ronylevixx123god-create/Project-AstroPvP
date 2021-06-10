package com.drtshock.playervaults.commands;

import org.bukkit.command.*;
import com.drtshock.playervaults.vaultmanagement.*;
import com.drtshock.playervaults.translations.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class DeleteCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (VaultOperations.isLocked()) {
            commandSender.sendMessage(Lang.TITLE + Lang.LOCKED.toString());
            return true;
        }
        switch (array.length) {
            case 1: {
                if (commandSender instanceof Player) {
                    VaultOperations.deleteOwnVault((Player)commandSender, array[0]);
                    break;
                }
                commandSender.sendMessage(Lang.TITLE.toString() + ChatColor.RED + Lang.PLAYER_ONLY);
                break;
            }
            case 2: {
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(array[0]);
                if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
                    commandSender.sendMessage(Lang.TITLE.toString() + Lang.NO_PLAYER_FOUND.toString().replaceAll("%p", array[0]));
                    break;
                }
                VaultOperations.deleteOtherVault(commandSender, offlinePlayer, array[1]);
                break;
            }
            default: {
                commandSender.sendMessage(Lang.TITLE + "/pvdel <number>");
                commandSender.sendMessage(Lang.TITLE + "/pvdel <player> <number>");
                break;
            }
        }
        return true;
    }
}
