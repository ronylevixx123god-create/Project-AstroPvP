package com.drtshock.playervaults.commands;

import org.bukkit.command.*;
import com.drtshock.playervaults.translations.*;
import org.bukkit.entity.*;
import com.drtshock.playervaults.*;
import com.drtshock.playervaults.vaultmanagement.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import java.util.*;

public class VaultCommand implements CommandExecutor {
    public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString) {
        if (VaultOperations.isLocked()) {
            paramCommandSender.sendMessage(Lang.TITLE + Lang.LOCKED.toString());
            return true;
        }
        if (paramCommandSender instanceof Player) {
            Player player = (Player)paramCommandSender;
            if (PlayerVaults.getInstance().getInVault().containsKey(player.getUniqueId().toString()))
                return true;
            switch (paramArrayOfString.length) {
                case 1:
                    if (VaultOperations.openOwnVault(player, paramArrayOfString[0])) {
                        PlayerVaults.getInstance().getInVault().put(player.getUniqueId().toString(), new VaultViewInfo(player.getUniqueId(), Integer.parseInt(paramArrayOfString[0])));
                    } else if (paramCommandSender.hasPermission("playervaults.admin")) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(paramArrayOfString[0]);
                        if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
                            paramCommandSender.sendMessage(Lang.TITLE.toString() + Lang.NO_PLAYER_FOUND.toString().replaceAll("%p", paramArrayOfString[0]));
                        } else {
                            YamlConfiguration yamlConfiguration = VaultManager.getInstance().getPlayerVaultFile(offlinePlayer.getUniqueId());
                            if (yamlConfiguration == null) {
                                paramCommandSender.sendMessage(Lang.TITLE.toString() + Lang.VAULT_DOES_NOT_EXIST.toString());
                            } else {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (String str : yamlConfiguration.getKeys(false))
                                    stringBuilder.append(str.replace("vault", "")).append(" ");
                                paramCommandSender.sendMessage(Lang.TITLE.toString() + Lang.EXISTING_VAULTS.toString().replaceAll("%p", paramArrayOfString[0]).replaceAll("%v", stringBuilder.toString().trim()));
                            }
                        }
                    }
                    return true;
                case 2:
                    if (!player.hasPermission("playervaults.admin")) {
                        player.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS.toString());
                    } else {
                        int i;
                        try {
                            i = Integer.parseInt(paramArrayOfString[1]);
                        } catch (NumberFormatException numberFormatException) {
                            player.sendMessage(Lang.TITLE.toString() + ChatColor.RED + Lang.MUST_BE_NUMBER);
                            return true;
                        }
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(paramArrayOfString[0]);
                        if (offlinePlayer != null && VaultOperations.openOtherVault(player, offlinePlayer.getUniqueId(), paramArrayOfString[1])) {
                            PlayerVaults.getInstance().getInVault().put(player.getUniqueId().toString(), new VaultViewInfo(offlinePlayer.getUniqueId(), i));
                        } else {
                            paramCommandSender.sendMessage(Lang.TITLE.toString() + Lang.NO_PLAYER_FOUND.toString().replaceAll("%p", paramArrayOfString[0]));
                        }
                    }
                    return true;
            }
            paramCommandSender.sendMessage(Lang.TITLE.toString() + Lang.HELP.toString());
        } else {
            paramCommandSender.sendMessage(Lang.TITLE.toString() + ChatColor.RED + Lang.PLAYER_ONLY.toString());
        }
        return true;
    }
}
