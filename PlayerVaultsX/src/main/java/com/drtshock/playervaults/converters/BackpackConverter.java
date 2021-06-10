package com.drtshock.playervaults.converters;

import org.bukkit.command.*;
import com.drtshock.playervaults.*;
import java.io.*;
import com.drtshock.playervaults.vaultmanagement.*;
import org.bukkit.*;
import org.bukkit.configuration.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.configuration.file.*;

public class BackpackConverter implements Converter
{
    @Override
    public int run(final CommandSender commandSender) {
        final File file = new File(PlayerVaults.getInstance().getDataFolder().getParentFile(), "Backpack" + File.separator + "backpacks");
        if (!file.exists()) {
            return -1;
        }
        int n = 0;
        final File[] listFiles = file.listFiles();
        int n2 = 1;
        for (final File file2 : (listFiles != null) ? listFiles : new File[0]) {
            if (file2.isDirectory()) {
                n += this.convert(file2, n2);
                ++n2;
            }
        }
        return n;
    }
    
    private int convert(final File file, final int n) {
        final PlayerVaults instance = PlayerVaults.getInstance();
        final VaultManager instance2 = VaultManager.getInstance();
        int i = 0;
        long currentTimeMillis = 0L;
        final File[] listFiles = file.listFiles();
        for (final File file2 : (listFiles != null) ? listFiles : new File[0]) {
            if (file2.isFile() && file2.getName().toLowerCase().endsWith(".yml")) {
                try {
                    final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(file2.getName().substring(0, file2.getName().lastIndexOf(46)));
                    if (offlinePlayer == null || offlinePlayer.getUniqueId() == null) {
                        instance.getLogger().warning("Unable to convert Backpack for player: " + ((offlinePlayer != null) ? offlinePlayer.getName() : file2.getName()));
                    }
                    else {
                        final UUID uniqueId = offlinePlayer.getUniqueId();
                        final ConfigurationSection configurationSection = ((FileConfiguration)YamlConfiguration.loadConfiguration(file2)).getConfigurationSection("backpack");
                        if (configurationSection.getKeys(false).size() > 0) {
                            Inventory inventory = instance2.getVault(uniqueId, n);
                            if (inventory == null) {
                                inventory = instance.getServer().createInventory((InventoryHolder)null, configurationSection.getKeys(false).size());
                            }
                            for (final String s : configurationSection.getKeys(false)) {
                                final ItemStack itemStack = configurationSection.getConfigurationSection(s).getItemStack("ItemStack");
                                if (itemStack == null) {
                                    continue;
                                }
                                inventory.setItem(Integer.parseInt(s.split(" ")[1]), itemStack);
                            }
                            instance2.saveVault(inventory, uniqueId, n);
                            ++i;
                            if (System.currentTimeMillis() - currentTimeMillis >= 1500L) {
                                instance.getLogger().info(i + " backpacks have been converted in " + file.getAbsolutePath());
                                currentTimeMillis = System.currentTimeMillis();
                            }
                        }
                    }
                }
                catch (Exception ex) {
                    instance.getLogger().warning("Error converting " + file2.getAbsolutePath());
                    ex.printStackTrace();
                }
            }
        }
        return i;
    }
    
    @Override
    public boolean canConvert() {
        final File parent = new File(PlayerVaults.getInstance().getDataFolder().getParentFile(), "Backpack");
        return parent.exists() && new File(parent, "backpacks").exists();
    }
    
    @Override
    public String getName() {
        return "Backpack";
    }
}
