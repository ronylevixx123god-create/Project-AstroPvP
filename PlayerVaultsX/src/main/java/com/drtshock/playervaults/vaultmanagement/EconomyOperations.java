package com.drtshock.playervaults.vaultmanagement;

import com.drtshock.playervaults.*;
import org.bukkit.entity.*;
import com.drtshock.playervaults.translations.*;
import java.io.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;

public class EconomyOperations
{
    private static final PlayerVaults PLUGIN;
    private static final FileConfiguration BUKKIT_CONFIG;
    
    public static boolean payToOpen(final Player player, final int n) {
        if (!EconomyOperations.PLUGIN.isEconomyEnabled() || player.hasPermission("playervaults.free")) {
            return true;
        }
        if (!VaultManager.getInstance().vaultExists(player.getUniqueId(), n)) {
            return payToCreate(player);
        }
        final double double1 = EconomyOperations.BUKKIT_CONFIG.getDouble("economy.cost-to-open", 10.0);
        if (PlayerVaults.getInstance().getEconomy().withdrawPlayer((OfflinePlayer)player, double1).transactionSuccess()) {
            player.sendMessage(Lang.TITLE.toString() + Lang.COST_TO_OPEN.toString().replaceAll("%price", "" + double1));
            return true;
        }
        return false;
    }
    
    public static boolean payToCreate(final Player player) {
        if (!EconomyOperations.PLUGIN.isEconomyEnabled() || player.hasPermission("playervaults.free")) {
            return true;
        }
        final double double1 = EconomyOperations.BUKKIT_CONFIG.getDouble("economy.cost-to-create", 100.0);
        if (PlayerVaults.getInstance().getEconomy().withdrawPlayer((OfflinePlayer)player, double1).transactionSuccess()) {
            player.sendMessage(Lang.TITLE.toString() + Lang.COST_TO_CREATE.toString().replaceAll("%price", "" + double1));
            return true;
        }
        return false;
    }
    
    public static boolean refundOnDelete(final Player player, final int i) {
        if (!EconomyOperations.PLUGIN.isEconomyEnabled() || player.hasPermission("playervaults.free")) {
            return true;
        }
        final File file = new File(EconomyOperations.PLUGIN.getVaultData(), player.getUniqueId().toString() + ".yml");
        if (!file.exists()) {
            player.sendMessage(Lang.TITLE.toString() + ChatColor.RED + Lang.VAULT_DOES_NOT_EXIST);
            return false;
        }
        if (YamlConfiguration.loadConfiguration(file).getString("vault" + i) == null) {
            player.sendMessage(Lang.TITLE.toString() + ChatColor.RED + Lang.VAULT_DOES_NOT_EXIST);
            return false;
        }
        final double double1 = EconomyOperations.BUKKIT_CONFIG.getDouble("economy.refund-on-delete");
        if (PlayerVaults.getInstance().getEconomy().depositPlayer((OfflinePlayer)player, double1).transactionSuccess()) {
            player.sendMessage(Lang.TITLE.toString() + Lang.REFUND_AMOUNT.toString().replaceAll("%price", String.valueOf(double1)));
            return true;
        }
        return false;
    }
    
    static {
        PLUGIN = PlayerVaults.getInstance();
        BUKKIT_CONFIG = EconomyOperations.PLUGIN.getConfig();
    }
}
