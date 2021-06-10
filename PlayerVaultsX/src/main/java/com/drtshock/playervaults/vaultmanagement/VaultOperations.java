package com.drtshock.playervaults.vaultmanagement;

import java.util.concurrent.atomic.*;
import com.drtshock.playervaults.*;
import org.bukkit.entity.*;
import com.drtshock.playervaults.translations.*;
import org.bukkit.command.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import java.io.*;

public class VaultOperations
{
    private static final AtomicBoolean LOCKED;
    
    public static boolean isLocked() {
        return VaultOperations.LOCKED.get();
    }
    
    public static void setLocked(final boolean newValue) {
        VaultOperations.LOCKED.set(newValue);
        if (newValue) {
            for (final Player player : PlayerVaults.getInstance().getServer().getOnlinePlayers()) {
                if (player.getOpenInventory() != null && player.getOpenInventory().getTopInventory().getHolder() instanceof VaultHolder) {
                    player.closeInventory();
                    player.sendMessage(Lang.TITLE + Lang.LOCKED.toString());
                }
            }
        }
    }
    
    public static boolean checkPerms(final CommandSender commandSender, final int i) {
        if (commandSender.hasPermission("playervaults.amount." + String.valueOf(i))) {
            return true;
        }
        for (int j = i; j <= 99; ++j) {
            if (commandSender.hasPermission("playervaults.amount." + String.valueOf(j))) {
                return true;
            }
        }
        return false;
    }
    
    public static int getMaxVaultSize(final UUID uuid) {
        return getMaxVaultSize(Bukkit.getOfflinePlayer(uuid));
    }
    
    public static int getMaxVaultSize(final OfflinePlayer offlinePlayer) {
        if (offlinePlayer == null || !offlinePlayer.isOnline()) {
            return 54;
        }
        for (int i = 6; i != 0; --i) {
            if (offlinePlayer.getPlayer().hasPermission("playervaults.size." + i)) {
                return i * 9;
            }
        }
        return 54;
    }
    
    public static boolean openOwnVault(final Player player, final String s) {
        if (isLocked()) {
            return false;
        }
        int int1;
        try {
            int1 = Integer.parseInt(s);
            if (int1 < 1) {
                return false;
            }
        }
        catch (NumberFormatException ex) {
            player.sendMessage(Lang.TITLE.toString() + Lang.MUST_BE_NUMBER.toString());
            return false;
        }
        if (!checkPerms((CommandSender)player, int1)) {
            player.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
            return false;
        }
        if (!EconomyOperations.payToOpen(player, int1)) {
            player.sendMessage(Lang.TITLE.toString() + Lang.INSUFFICIENT_FUNDS);
            return false;
        }
        final Inventory loadOwnVault = VaultManager.getInstance().loadOwnVault(player, int1, getMaxVaultSize((OfflinePlayer)player));
        if (loadOwnVault == null) {
            PlayerVaults.debug(String.format("Failed to open null vault %d for %s. This is weird.", int1, player.getName()));
            return false;
        }
        player.openInventory(loadOwnVault);
        player.sendMessage(Lang.TITLE.toString() + Lang.OPEN_VAULT.toString().replace("%v", s));
        return true;
    }
    
    public static boolean openOwnVault(final Player player, final String s, final boolean b) {
        if (b && player.hasPermission("playervaults.commands.use")) {
            return openOwnVault(player, s);
        }
        player.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS.toString());
        return false;
    }
    
    public static boolean openOtherVault(final Player player, final UUID uuid, final String s) {
        if (isLocked()) {
            return false;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        int int1 = 0;
        try {
            int1 = Integer.parseInt(s);
            if (int1 < 1) {
                player.sendMessage(Lang.TITLE.toString() + ChatColor.RED + Lang.MUST_BE_NUMBER);
                return false;
            }
        }
        catch (NumberFormatException ex) {
            player.sendMessage(Lang.TITLE.toString() + ChatColor.RED + Lang.MUST_BE_NUMBER);
        }
        final Inventory loadOtherVault = VaultManager.getInstance().loadOtherVault(uuid, int1, getMaxVaultSize(uuid));
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        final String replacement = (offlinePlayer != null) ? offlinePlayer.getName() : "";
        if (loadOtherVault == null) {
            player.sendMessage(Lang.TITLE.toString() + Lang.VAULT_DOES_NOT_EXIST.toString());
            PlayerVaults.debug("opening other vault returning false", currentTimeMillis);
            return false;
        }
        player.openInventory(loadOtherVault);
        player.sendMessage(Lang.TITLE.toString() + Lang.OPEN_OTHER_VAULT.toString().replace("%v", s).replace("%p", replacement));
        PlayerVaults.debug("opening other vault", currentTimeMillis);
        return true;
    }
    
    public static void deleteOwnVault(final Player player, final String s) {
        if (isLocked()) {
            return;
        }
        if (isNumber(s)) {
            int int1 = 0;
            try {
                int1 = Integer.parseInt(s);
                if (int1 == 0) {
                    player.sendMessage(Lang.TITLE.toString() + ChatColor.RED + Lang.MUST_BE_NUMBER);
                    return;
                }
            }
            catch (NumberFormatException ex) {
                player.sendMessage(Lang.TITLE.toString() + ChatColor.RED + Lang.MUST_BE_NUMBER);
            }
            if (EconomyOperations.refundOnDelete(player, int1)) {
                VaultManager.getInstance().deleteVault((CommandSender)player, player.getUniqueId(), int1);
            }
        }
        else {
            player.sendMessage(Lang.TITLE.toString() + Lang.MUST_BE_NUMBER);
        }
    }
    
    public static void deleteOtherVault(final CommandSender commandSender, final OfflinePlayer offlinePlayer, final String s) {
        if (isLocked()) {
            return;
        }
        if (commandSender.hasPermission("playervaults.delete")) {
            if (isNumber(s)) {
                int int1 = 0;
                try {
                    int1 = Integer.parseInt(s);
                    if (int1 == 0) {
                        commandSender.sendMessage(Lang.TITLE.toString() + ChatColor.RED + Lang.MUST_BE_NUMBER);
                        return;
                    }
                }
                catch (NumberFormatException ex) {
                    commandSender.sendMessage(Lang.TITLE.toString() + ChatColor.RED + Lang.MUST_BE_NUMBER);
                }
                VaultManager.getInstance().deleteVault(commandSender, offlinePlayer.getUniqueId(), int1);
            }
            else {
                commandSender.sendMessage(Lang.TITLE.toString() + Lang.MUST_BE_NUMBER);
            }
        }
        else {
            commandSender.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
        }
    }
    
    private static boolean isNumber(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }
    
    static {
        LOCKED = new AtomicBoolean(false);
    }
}
