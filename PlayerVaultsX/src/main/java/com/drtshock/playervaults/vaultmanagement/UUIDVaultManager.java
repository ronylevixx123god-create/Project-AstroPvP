package com.drtshock.playervaults.vaultmanagement;

import org.bukkit.configuration.file.*;
import com.drtshock.playervaults.*;
import java.util.concurrent.*;
import org.bukkit.entity.*;
import com.drtshock.playervaults.translations.*;
import org.bukkit.inventory.*;
import org.bukkit.configuration.*;
import java.util.*;
import org.bukkit.command.*;
import org.bukkit.scheduler.*;
import java.io.*;
import org.bukkit.plugin.*;
import java.util.logging.*;
import org.bukkit.*;

@Deprecated
public class UUIDVaultManager
{
    private static UUIDVaultManager instance;
    private final File directory;
    private final Map<String, YamlConfiguration> cachedVaultFiles;
    
    public UUIDVaultManager() {
        this.directory = PlayerVaults.getInstance().getUuidData();
        this.cachedVaultFiles = new ConcurrentHashMap<String, YamlConfiguration>();
        UUIDVaultManager.instance = this;
    }
    
    public void saveVault(final Inventory inventory, final String s, final int i) {
        final int size = inventory.getSize();
        final YamlConfiguration playerVaultFile = this.getPlayerVaultFile(s);
        if (size == 54) {
            playerVaultFile.set("vault" + i, (Object)null);
        }
        else {
            for (int j = 0; j < size; ++j) {
                playerVaultFile.set("vault" + i + "." + j, (Object)null);
            }
        }
        final List<String> string = Serialization.toString(inventory);
        final String[] array = string.toArray(new String[string.size()]);
        for (int k = 0; k < array.length; ++k) {
            if (!array[k].equalsIgnoreCase("null")) {
                playerVaultFile.set("vault" + i + "." + k, (Object)array[k]);
            }
        }
        this.saveFileSync(s, playerVaultFile);
    }
    
    public Inventory loadOwnVault(final Player player, final int n, int n2) {
        if (n2 % 9 != 0) {
            n2 = 54;
        }
        final String replace = Lang.VAULT_TITLE.toString().replace("%number", String.valueOf(n)).replace("%p", player.getName());
        final VaultViewInfo vaultViewInfo = new VaultViewInfo(player.getUniqueId(), n);
        Inventory inventory;
        if (PlayerVaults.getInstance().getOpenInventories().containsKey(vaultViewInfo.toString())) {
            inventory = PlayerVaults.getInstance().getOpenInventories().get(vaultViewInfo.toString());
        }
        else {
            final YamlConfiguration playerVaultFile = this.getPlayerVaultFile(player.getUniqueId());
            if (playerVaultFile.getConfigurationSection("vault" + n) == null) {
                final VaultHolder vaultHolder = new VaultHolder(n);
                if (!EconomyOperations.payToCreate(player)) {
                    player.sendMessage(Lang.TITLE.toString() + Lang.INSUFFICIENT_FUNDS.toString());
                    return null;
                }
                inventory = Bukkit.createInventory((InventoryHolder)vaultHolder, n2, replace);
                vaultHolder.setInventory(inventory);
            }
            else {
                final Inventory inventory2 = this.getInventory(playerVaultFile, n2, n, replace);
                if (inventory2 == null) {
                    return null;
                }
                inventory = inventory2;
            }
            PlayerVaults.getInstance().getOpenInventories().put(vaultViewInfo.toString(), inventory);
        }
        return inventory;
    }
    
    public Inventory loadOtherVault(final String s, final int i, int n) {
        if (n % 9 != 0) {
            n = 54;
        }
        final String replace = Lang.VAULT_TITLE.toString().replace("%number", String.valueOf(i)).replace("%p", PlayerVaults.getInstance().getNameIfPlayer(s));
        final VaultViewInfo vaultViewInfo = new VaultViewInfo(s, i);
        Inventory value;
        if (PlayerVaults.getInstance().getOpenInventories().containsKey(vaultViewInfo.toString())) {
            value = PlayerVaults.getInstance().getOpenInventories().get(vaultViewInfo.toString());
        }
        else {
            final Inventory inventory = this.getInventory(this.getPlayerVaultFile(s), n, i, replace);
            if (inventory == null) {
                return null;
            }
            value = inventory;
            PlayerVaults.getInstance().getOpenInventories().put(vaultViewInfo.toString(), value);
        }
        return value;
    }
    
    private Inventory getInventory(final YamlConfiguration yamlConfiguration, final int n, final int i, final String s) {
        final ArrayList<String> list = new ArrayList<String>();
        for (int j = 0; j < n; ++j) {
            final String string = yamlConfiguration.getString("vault" + i + "." + j);
            if (string != null) {
                list.add(string);
            }
            else {
                list.add("null");
            }
        }
        return Serialization.toInventory(list, i, n, s);
    }
    
    public Inventory getVault(final UUID uuid, final int n) {
        final ConfigurationSection configurationSection = this.getPlayerVaultFile(uuid).getConfigurationSection("vault" + n);
        final int maxVaultSize = VaultOperations.getMaxVaultSize(uuid);
        final String replace = Lang.VAULT_TITLE.toString().replace("%number", String.valueOf(n));
        if (configurationSection == null) {
            final VaultHolder vaultHolder = new VaultHolder(n);
            final Inventory inventory = Bukkit.createInventory((InventoryHolder)vaultHolder, maxVaultSize, replace);
            vaultHolder.setInventory(inventory);
            return inventory;
        }
        final ArrayList<String> list = new ArrayList<String>();
        final Iterator iterator = configurationSection.getKeys(false).iterator();
        while (iterator.hasNext()) {
            list.add(configurationSection.getString((String)iterator.next()));
        }
        return Serialization.toInventory(list, n, maxVaultSize, replace);
    }
    
    public boolean vaultExists(final String str, final int i) {
        return new File(this.directory, str + ".yml").exists() && this.getPlayerVaultFile(str).contains("vault" + i);
    }
    
    public void deleteVault(final CommandSender commandSender, final String s, final int n) {
        new BukkitRunnable() {
            public void run() {
                final File file = new File(UUIDVaultManager.this.directory, s + ".yml");
                if (!file.exists()) {
                    return;
                }
                final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(file);
                if (file.exists()) {
                    loadConfiguration.set("vault" + n, (Object)null);
                    if (UUIDVaultManager.this.cachedVaultFiles.containsKey(s)) {
                        UUIDVaultManager.this.cachedVaultFiles.put(s, loadConfiguration);
                    }
                    try {
                        loadConfiguration.save(file);
                    }
                    catch (IOException ex) {}
                }
            }
        }.runTaskAsynchronously((Plugin)PlayerVaults.getInstance());
        final Player player = Bukkit.getPlayer(s);
        if (player != null) {
            if (commandSender.getName().equalsIgnoreCase(((OfflinePlayer)player).getName())) {
                commandSender.sendMessage(Lang.TITLE.toString() + Lang.DELETE_VAULT.toString().replace("%v", String.valueOf(n)));
            }
            else {
                commandSender.sendMessage(Lang.TITLE.toString() + Lang.DELETE_OTHER_VAULT.toString().replace("%v", String.valueOf(n)).replaceAll("%p", ((OfflinePlayer)player).getName()));
            }
        }
        PlayerVaults.getInstance().getOpenInventories().remove(new VaultViewInfo(s, n).toString());
    }
    
    public void cachePlayerVaultFile(final String s) {
        final YamlConfiguration loadPlayerVaultFile = this.loadPlayerVaultFile(s, false);
        if (loadPlayerVaultFile != null) {
            this.cachedVaultFiles.put(s, loadPlayerVaultFile);
        }
    }
    
    public void removeCachedPlayerVaultFile(final String s) {
        if (this.cachedVaultFiles.containsKey(s)) {
            this.cachedVaultFiles.remove(s);
        }
    }
    
    @Deprecated
    public YamlConfiguration getPlayerVaultFile(final UUID uuid) {
        return this.getPlayerVaultFile(uuid.toString());
    }
    
    public YamlConfiguration getPlayerVaultFile(final String s) {
        if (this.cachedVaultFiles.containsKey(s)) {
            return this.cachedVaultFiles.get(s);
        }
        return this.loadPlayerVaultFile(s);
    }
    
    public YamlConfiguration loadPlayerVaultFile(final String s) {
        return this.loadPlayerVaultFile(s, true);
    }
    
    public YamlConfiguration loadPlayerVaultFile(final String str, final boolean b) {
        if (!this.directory.exists()) {
            this.directory.mkdir();
        }
        final File file = new File(this.directory, str + ".yml");
        if (!file.exists()) {
            if (!b) {
                return null;
            }
            try {
                file.createNewFile();
            }
            catch (IOException ex) {}
        }
        return YamlConfiguration.loadConfiguration(file);
    }
    
    public void saveFileSync(final String str, final YamlConfiguration yamlConfiguration) {
        if (this.cachedVaultFiles.containsKey(str)) {
            this.cachedVaultFiles.put(str, yamlConfiguration);
        }
        final boolean backupsEnabled = PlayerVaults.getInstance().isBackupsEnabled();
        final File backupsFolder = PlayerVaults.getInstance().getBackupsFolder();
        final File file = new File(this.directory, str + ".yml");
        if (file.exists() && backupsEnabled) {
            file.renameTo(new File(backupsFolder, str + ".yml"));
        }
        try {
            yamlConfiguration.save(file);
        }
        catch (IOException thrown) {
            PlayerVaults.getInstance().getLogger().log(Level.SEVERE, "Failed to save vault file for: " + str, thrown);
        }
    }
    
    public static UUIDVaultManager getInstance() {
        return UUIDVaultManager.instance;
    }
}
