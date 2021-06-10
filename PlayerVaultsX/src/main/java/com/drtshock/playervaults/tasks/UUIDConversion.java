package com.drtshock.playervaults.tasks;

import com.drtshock.playervaults.*;
import com.google.common.io.*;
import java.io.*;
import java.util.logging.*;
import org.bukkit.*;

public final class UUIDConversion implements Runnable
{
    @Override
    public void run() {
        final Logger logger = PlayerVaults.getInstance().getLogger();
        if (PlayerVaults.getInstance().getVaultData().exists()) {
            return;
        }
        final File file = new File(PlayerVaults.getInstance().getDataFolder() + File.separator + "vaults");
        if (file.exists()) {
            logger.info("********** Starting conversion to UUIDs for PlayerVaults **********");
            logger.info("This might take awhile.");
            logger.info(file.toString() + " will remain as a backup.");
            for (final File file2 : file.listFiles()) {
                if (!file2.isDirectory()) {
                    final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(file2.getName().replace(".yml", ""));
                    if (offlinePlayer == null) {
                        logger.warning("Unable to convert file because player never joined the server: " + file2.getName());
                        break;
                    }
                    final File file3 = new File(PlayerVaults.getInstance().getVaultData(), offlinePlayer.getUniqueId().toString() + ".yml");
                    file2.mkdirs();
                    try {
                        Files.copy(file2, file3);
                        logger.info("Successfully converted vault file for " + offlinePlayer.getName());
                    }
                    catch (IOException ex) {
                        logger.severe("Couldn't convert vault file for " + offlinePlayer.getName());
                    }
                }
            }
            logger.info("********** Conversion done ;D **********");
        }
    }
}
