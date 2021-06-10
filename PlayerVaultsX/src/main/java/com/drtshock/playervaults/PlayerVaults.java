package com.drtshock.playervaults;

import org.bukkit.plugin.java.*;
import org.bukkit.inventory.*;
import net.milkbowl.vault.economy.*;
import org.bukkit.configuration.file.*;
import com.drtshock.playervaults.vaultmanagement.*;
import org.bukkit.event.*;
import com.drtshock.playervaults.tasks.*;
import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.plugin.*;
import java.util.logging.*;
import com.drtshock.playervaults.commands.*;
import com.drtshock.playervaults.listeners.*;
import com.drtshock.playervaults.translations.*;
import java.util.*;
import org.bukkit.*;
import java.io.*;
import java.net.*;

public class PlayerVaults extends JavaPlugin
{
    private static PlayerVaults instance;
    public static boolean DEBUG;
    private final HashMap<String, SignSetInfo> setSign;
    private final HashMap<String, VaultViewInfo> inVault;
    private final HashMap<String, Inventory> openInventories;
    private Economy economy;
    private boolean useVault;
    private YamlConfiguration signs;
    private File signsFile;
    private boolean saveQueued;
    private boolean backupsEnabled;
    private File backupsFolder;
    private File uuidData;
    private File vaultData;
    private final Set<Material> blockedMats;
    
    public PlayerVaults() {
        this.setSign = new HashMap<String, SignSetInfo>();
        this.inVault = new HashMap<String, VaultViewInfo>();
        this.openInventories = new HashMap<String, Inventory>();
        this.economy = null;
        this.useVault = false;
        this.backupsFolder = null;
        this.blockedMats = new HashSet<Material>();
    }
    
    public void onEnable() {
        loadConfig();
        (PlayerVaults.instance = this).loadConfig();
        PlayerVaults.DEBUG = this.getConfig().getBoolean("debug", false);
        debug("config", System.currentTimeMillis());
        this.uuidData = new File(this.getDataFolder(), "uuidvaults");
        this.vaultData = new File(this.getDataFolder(), "base64vaults");
        debug("vaultdata", System.currentTimeMillis());
        this.getServer().getScheduler().runTask((Plugin)this, (Runnable)new UUIDConversion());
        debug("uuid conversion", System.currentTimeMillis());
        new VaultManager();
        this.getServer().getScheduler().runTask((Plugin)this, (Runnable)new Base64Conversion());
        debug("base64 conversion", System.currentTimeMillis());
        this.loadLang();
        debug("lang", System.currentTimeMillis());
        new UUIDVaultManager();
        debug("uuidvaultmanager", System.currentTimeMillis());
        this.getServer().getPluginManager().registerEvents((Listener)new Listeners(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new VaultPreloadListener(), (Plugin)this);
        debug("registering listeners", System.currentTimeMillis());
        this.backupsEnabled = this.getConfig().getBoolean("backups.enabled", true);
        this.loadSigns();
        debug("loaded signs", System.currentTimeMillis());
        debug("check update", System.currentTimeMillis());
        this.getCommand("pv").setExecutor((CommandExecutor)new VaultCommand());
        this.getCommand("pvdel").setExecutor((CommandExecutor)new DeleteCommand());
        this.getCommand("pvconvert").setExecutor((CommandExecutor)new ConvertCommand());
        debug("registered commands", System.currentTimeMillis());
        this.useVault = this.setupEconomy();
        debug("setup economy", System.currentTimeMillis());
        if (this.getConfig().getBoolean("cleanup.enable", false)) {
            this.getServer().getScheduler().runTaskAsynchronously((Plugin)this, (Runnable)new Cleanup(this.getConfig().getInt("cleanup.lastEdit", 30)));
            debug("cleanup task", System.currentTimeMillis());
        }
        new BukkitRunnable() {
            public void run() {
                if (PlayerVaults.this.saveQueued) {
                    PlayerVaults.this.saveSignsFile();
                }
            }
        }.runTaskTimer((Plugin)this, 20L, 20L);
        debug("enable done", System.currentTimeMillis());
    }
    
    public void onDisable() {
        for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (this.inVault.containsKey(player.getUniqueId().toString())) {
                final Inventory topInventory = player.getOpenInventory().getTopInventory();
                if (topInventory.getViewers().size() == 1) {
                    final VaultViewInfo vaultViewInfo = this.inVault.get(player.getUniqueId().toString());
                    VaultManager.getInstance().saveVault(topInventory, player.getUniqueId(), vaultViewInfo.getNumber());
                    this.openInventories.remove(vaultViewInfo.toString());
                    player.closeInventory();
                }
                this.inVault.remove(player.getUniqueId().toString());
                debug("Closing vault for " + player.getName());
                player.closeInventory();
            }
        }
        if (this.getConfig().getBoolean("cleanup.enable", false)) {
            this.saveSignsFile();
        }
    }
    
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (command.getName().equalsIgnoreCase("pvreload")) {
            this.reloadConfig();
            this.loadConfig();
            this.loadLang();
            commandSender.sendMessage(ChatColor.GREEN + "Reloaded PlayerVault's configuration and lang files.");
        }
        return true;
    }
    
    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        final RegisteredServiceProvider registration = this.getServer().getServicesManager().getRegistration((Class)Economy.class);
        if (registration == null) {
            return false;
        }
        this.economy = (Economy)registration.getProvider();
        return this.economy != null;
    }
    
    private void loadConfig() {
        this.saveDefaultConfig();
        this.blockedMats.clear();
        if (this.getConfig().getBoolean("blockitems", false) && this.getConfig().contains("blocked-items")) {
            final Iterator<String> iterator = this.getConfig().getStringList("blocked-items").iterator();
            while (iterator.hasNext()) {
                final Material matchMaterial = Material.matchMaterial((String)iterator.next());
                if (matchMaterial != null) {
                    this.blockedMats.add(matchMaterial);
                    this.getLogger().log(Level.INFO, "Added {0} to list of blocked materials.", matchMaterial.name());
                }
            }
        }
    }
    
    private void loadSigns() {
        if (!this.getConfig().getBoolean("signs-enabled", true)) {
            return;
        }
        this.getCommand("pvsign").setExecutor((CommandExecutor)new SignCommand());
        this.getServer().getPluginManager().registerEvents((Listener)new SignListener(this), (Plugin)this);
        final File signsFile = new File(this.getDataFolder(), "signs.yml");
        if (!signsFile.exists()) {
            try {
                signsFile.createNewFile();
            }
            catch (IOException ex) {
                this.getLogger().severe("PlayerVaults has encountered a fatal error trying to load the signs file.");
                this.getLogger().severe("Please report this error to drtshock.");
                ex.printStackTrace();
            }
        }
        this.signsFile = signsFile;
        this.signs = YamlConfiguration.loadConfiguration(signsFile);
    }
    
    public YamlConfiguration getSigns() {
        return this.signs;
    }
    
    public void saveSigns() {
        this.saveQueued = true;
    }
    
    private void saveSignsFile() {
        if (!this.getConfig().getBoolean("signs-enabled", true)) {
            return;
        }
        this.saveQueued = false;
        try {
            this.signs.save(this.signsFile);
        }
        catch (IOException ex) {
            this.getLogger().severe("PlayerVaults has encountered an error trying to save the signs file.");
            this.getLogger().severe("Please report this error to drtshock.");
            ex.printStackTrace();
        }
    }
    
    public <T> void setInConfig(final String s, final T t, final YamlConfiguration yamlConfiguration) {
        yamlConfiguration.set(s, (Object)t);
    }
    
    public void loadLang() {
        final File parent = new File(this.getDataFolder(), "lang");
        if (!parent.exists()) {
            parent.mkdir();
        }
        final String string = this.getConfig().getString("language", "english");
        File file = null;
        File file2 = null;
        for (final Language language : Language.values()) {
            final String string2 = language.getFriendlyName() + ".yml";
            final File file3 = new File(parent, string2);
            if (language == Language.ENGLISH) {
                file = file3;
            }
            if (string.equalsIgnoreCase(language.getFriendlyName())) {
                file2 = file3;
            }
            if (!file3.exists()) {
                this.saveResource("lang/" + string2, false);
            }
        }
        if (file2 != null && !file2.exists()) {
            this.getLogger().severe("Failed to load language for " + string + ". Defaulting to English.");
            file2 = file;
        }
        if (file2 == null) {
            this.getLogger().severe("Failed to load custom language settings. Loading plugin defaults. This should never happen, go ask for help.");
            return;
        }
        Lang.setFile(YamlConfiguration.loadConfiguration(file2));
        this.getLogger().info("Loaded lang for " + string);
    }
    
    public HashMap<String, SignSetInfo> getSetSign() {
        return this.setSign;
    }
    
    public HashMap<String, VaultViewInfo> getInVault() {
        return this.inVault;
    }
    
    public HashMap<String, Inventory> getOpenInventories() {
        return this.openInventories;
    }
    
    public Economy getEconomy() {
        return this.economy;
    }
    
    public boolean isEconomyEnabled() {
        return this.getConfig().getBoolean("economy.enabled", false) && this.useVault;
    }
    
    public File getVaultData() {
        return this.vaultData;
    }
    
    @Deprecated
    public File getUuidData() {
        return this.uuidData;
    }
    
    public boolean isBackupsEnabled() {
        return this.backupsEnabled;
    }
    
    public File getBackupsFolder() {
        if (this.backupsFolder == null) {
            (this.backupsFolder = new File(this.getVaultData(), "backups")).mkdirs();
        }
        return this.backupsFolder;
    }
    
    public String getNameIfPlayer(final String name) {
        UUID fromString;
        try {
            fromString = UUID.fromString(name);
        }
        catch (Exception ex) {
            return name;
        }
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(fromString);
        return (offlinePlayer != null) ? offlinePlayer.getName() : name;
    }
    
    public boolean isBlockedMaterial(final Material material) {
        return this.blockedMats.contains(material);
    }
    
    public static PlayerVaults getInstance() {
        return PlayerVaults.instance;
    }
    
    public static void debug(final String s, final long n) {
        final long l = System.currentTimeMillis() - n;
        if (PlayerVaults.DEBUG || l > 4L) {
            Bukkit.getLogger().log(Level.INFO, "At {0}. Time since start: {1}ms", new Object[] { s, l });
        }
    }
    
    public static void debug(final String msg) {
        if (PlayerVaults.DEBUG) {
            Bukkit.getLogger().log(Level.INFO, msg);
        }
    }
    
    static {
        PlayerVaults.DEBUG = false;
    }
}
