package com.drtshock.playervaults.vaultmanagement;

import java.util.*;
import org.bukkit.configuration.file.*;
import com.drtshock.playervaults.*;
import java.util.concurrent.*;
import org.bukkit.entity.*;
import com.drtshock.playervaults.translations.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.command.*;
import org.bukkit.scheduler.*;
import java.io.*;
import org.bukkit.plugin.*;
import java.util.logging.*;

public class VaultManager
{
    private static VaultManager instance;
    private static final String VAULTKEY = "vault%d";
    private final File directory;
    private final Map<UUID, YamlConfiguration> cachedVaultFiles;
    
    public VaultManager() {
        this.directory = PlayerVaults.getInstance().getVaultData();
        this.cachedVaultFiles = new ConcurrentHashMap<UUID, YamlConfiguration>();
        VaultManager.instance = this;
    }
    
    public void saveVault(final Inventory inventory, final UUID uuid, final int i) {
        final YamlConfiguration playerVaultFile = this.getPlayerVaultFile(uuid);
        playerVaultFile.set(String.format("vault%d", i), (Object)Base64Serialization.toBase64(inventory));
        this.saveFileSync(uuid, playerVaultFile);
    }
    
    public Inventory loadOwnVault(final Player player, final int n, int n2) {
        if (n2 % 9 != 0) {
            n2 = 54;
        }
        final String replace = Lang.VAULT_TITLE.toString().replace("%number", String.valueOf(n)).replace("%p", player.getName());
        final VaultViewInfo vaultViewInfo = new VaultViewInfo(player.getUniqueId(), n);
        if (PlayerVaults.getInstance().getOpenInventories().containsKey(vaultViewInfo.toString())) {
            return PlayerVaults.getInstance().getOpenInventories().get(vaultViewInfo.toString());
        }
        final YamlConfiguration playerVaultFile = this.getPlayerVaultFile(player.getUniqueId());
        final VaultHolder vaultHolder = new VaultHolder(n);
        Inventory inventory;
        if (playerVaultFile.getString(String.format("vault%d", n)) == null) {
            inventory = Bukkit.createInventory((InventoryHolder)vaultHolder, n2, replace);
            vaultHolder.setInventory(inventory);
        }
        else {
            final Inventory inventory2 = this.getInventory((InventoryHolder)vaultHolder, playerVaultFile, n2, n, replace);
            if (inventory2 == null) {
                return null;
            }
            inventory = inventory2;
        }
        PlayerVaults.getInstance().getOpenInventories().put(vaultViewInfo.toString(), inventory);
        return inventory;
    }
    
    public Inventory loadOtherVault(final UUID uuid, final int i, int n) {
        if (n % 9 != 0) {
            n = 54;
        }
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        String name = "null";
        if (offlinePlayer != null) {
            name = offlinePlayer.getName();
        }
        final String replace = Lang.VAULT_TITLE.toString().replace("%number", String.valueOf(i)).replace("%p", name);
        final VaultViewInfo vaultViewInfo = new VaultViewInfo(uuid, i);
        final VaultHolder vaultHolder = new VaultHolder(i);
        Inventory value;
        if (PlayerVaults.getInstance().getOpenInventories().containsKey(vaultViewInfo.toString())) {
            value = PlayerVaults.getInstance().getOpenInventories().get(vaultViewInfo.toString());
        }
        else {
            final Inventory inventory = this.getInventory((InventoryHolder)vaultHolder, this.getPlayerVaultFile(uuid), n, i, replace);
            if (inventory == null) {
                return null;
            }
            value = inventory;
            PlayerVaults.getInstance().getOpenInventories().put(vaultViewInfo.toString(), value);
        }
        return value;
    }
    
    private Inventory getInventory(final InventoryHolder inventoryHolder, final YamlConfiguration yamlConfiguration, final int n, final int i, final String s) {
        final Inventory inventory = Bukkit.createInventory(inventoryHolder, n, s);
        final Inventory fromBase64 = Base64Serialization.fromBase64(yamlConfiguration.getString(String.format("vault%d", i)));
        if (fromBase64 == null) {
            return inventory;
        }
        if (fromBase64.getContents().length > n) {
            for (final ItemStack itemStack : fromBase64.getContents()) {
                if (itemStack != null) {
                    inventory.addItem(new ItemStack[] { itemStack });
                }
            }
        }
        else {
            inventory.setContents(fromBase64.getContents());
        }
        return inventory;
    }
    
    public Inventory getVault(final UUID uuid, final int i) {
        return Base64Serialization.fromBase64(this.getPlayerVaultFile(uuid).getString(String.format("vault%d", i)));
    }
    
    public boolean vaultExists(final UUID obj, final int i) {
        return new File(this.directory, obj + ".yml").exists() && this.getPlayerVaultFile(obj).contains(String.format("vault%d", i));
    }
    
    public void deleteVault(final CommandSender commandSender, final UUID uuid, final int n) {
        new BukkitRunnable() {
            public void run() {
                final File file = new File(VaultManager.this.directory, uuid + ".yml");
                if (!file.exists()) {
                    return;
                }
                final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(file);
                if (file.exists()) {
                    loadConfiguration.set(String.format("vault%d", n), (Object)null);
                    if (VaultManager.this.cachedVaultFiles.containsKey(uuid)) {
                        VaultManager.this.cachedVaultFiles.put(uuid, loadConfiguration);
                    }
                    try {
                        loadConfiguration.save(file);
                    }
                    catch (IOException ex) {}
                }
            }
        }.runTaskAsynchronously((Plugin)PlayerVaults.getInstance());
        final Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            if (commandSender.getName().equalsIgnoreCase(((OfflinePlayer)player).getName())) {
                commandSender.sendMessage(Lang.TITLE.toString() + Lang.DELETE_VAULT.toString().replace("%v", String.valueOf(n)));
            }
            else {
                commandSender.sendMessage(Lang.TITLE.toString() + Lang.DELETE_OTHER_VAULT.toString().replace("%v", String.valueOf(n)).replaceAll("%p", ((OfflinePlayer)player).getName()));
            }
        }
        PlayerVaults.getInstance().getOpenInventories().remove(new VaultViewInfo(uuid, n).toString());
    }
    
    public void cachePlayerVaultFile(final UUID uuid) {
        final YamlConfiguration loadPlayerVaultFile = this.loadPlayerVaultFile(uuid, false);
        if (loadPlayerVaultFile != null) {
            this.cachedVaultFiles.put(uuid, loadPlayerVaultFile);
        }
    }
    
    public void removeCachedPlayerVaultFile(final UUID uuid) {
        if (this.cachedVaultFiles.containsKey(uuid)) {
            this.cachedVaultFiles.remove(uuid);
        }
    }
    
    public YamlConfiguration getPlayerVaultFile(final UUID uuid) {
        if (this.cachedVaultFiles.containsKey(uuid)) {
            return this.cachedVaultFiles.get(uuid);
        }
        return this.loadPlayerVaultFile(uuid);
    }
    
    public YamlConfiguration loadPlayerVaultFile(final UUID uuid) {
        return this.loadPlayerVaultFile(uuid, true);
    }
    
    public YamlConfiguration loadPlayerVaultFile(final UUID uuid, final boolean b) {
        if (!this.directory.exists()) {
            this.directory.mkdir();
        }
        final File file = new File(this.directory, uuid.toString() + ".yml");
        if (!file.exists()) {
            if (!b) {
                return null;
            }
            try {
                file.createNewFile();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }
    
    public void saveFileSync(final UUID uuid, final YamlConfiguration yamlConfiguration) {
        if (this.cachedVaultFiles.containsKey(uuid)) {
            this.cachedVaultFiles.put(uuid, yamlConfiguration);
        }
        final boolean backupsEnabled = PlayerVaults.getInstance().isBackupsEnabled();
        final File backupsFolder = PlayerVaults.getInstance().getBackupsFolder();
        final File file = new File(this.directory, uuid.toString() + ".yml");
        if (file.exists() && backupsEnabled) {
            file.renameTo(new File(backupsFolder, uuid.toString() + ".yml"));
        }
        try {
            yamlConfiguration.save(file);
        }
        catch (IOException thrown) {
            PlayerVaults.getInstance().getLogger().log(Level.SEVERE, "Failed to save vault file for: " + uuid.toString(), thrown);
        }
    }
    
    public static VaultManager getInstance() {
        return VaultManager.instance;
    }
}
