package com.drtshock.playervaults.tasks;

import com.drtshock.playervaults.*;
import com.drtshock.playervaults.vaultmanagement.*;
import java.util.logging.*;
import java.io.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.configuration.file.*;

public final class Base64Conversion implements Runnable
{
    @Override
    public void run() {
        final Logger logger = PlayerVaults.getInstance().getLogger();
        final File vaultData = PlayerVaults.getInstance().getVaultData();
        if (vaultData.exists()) {
            return;
        }
        vaultData.mkdirs();
        final File uuidData = PlayerVaults.getInstance().getUuidData();
        if (!uuidData.exists()) {
            logger.info("No uuidvaults found to convert to base64.");
            return;
        }
        vaultData.mkdirs();
        final UUIDVaultManager instance = UUIDVaultManager.getInstance();
        final VaultManager instance2 = VaultManager.getInstance();
        logger.info("********** Starting conversion to Base64 for PlayerVaults **********");
        logger.info("This might take awhile.");
        logger.info(uuidData.toString() + " will remain as a backup.");
        int i = 0;
        int j = 0;
        int k = 0;
        for (final File file : uuidData.listFiles()) {
            Label_0401: {
                if (!file.isDirectory()) {
                    final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(file);
                    final String replace = file.getName().replace(".yml", "");
                    UUID obj;
                    try {
                        obj = UUID.fromString(replace);
                    }
                    catch (Exception ex) {
                        logger.warning(String.format("Failed to parse uuid for %s. Trying to convert", replace));
                        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(replace);
                        if (offlinePlayer == null) {
                            logger.warning(String.format("Failed to convert name to uuid for %s", replace));
                            break Label_0401;
                        }
                        logger.info(String.format("Successfully got UUID for %s", replace));
                        obj = offlinePlayer.getUniqueId();
                    }
                    for (final String s : ((FileConfiguration)loadConfiguration).getKeys(false)) {
                        if (!s.startsWith("vault")) {
                            continue;
                        }
                        final int intValue = Integer.valueOf(s.replace("vault", ""));
                        try {
                            instance2.saveVault(instance.getVault(obj, intValue), obj, intValue);
                            ++j;
                        }
                        catch (Exception ex2) {
                            logger.severe("Failed to parse vault " + intValue + " for " + obj);
                            ++k;
                        }
                    }
                    ++i;
                }
            }
        }
        logger.info(String.format("Converted %d vaults for %d players to base64. %d failed to convert", j, i, k));
    }
}
