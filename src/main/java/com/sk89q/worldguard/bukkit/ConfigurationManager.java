/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.util.yaml.YAMLFormat
 *  com.sk89q.util.yaml.YAMLProcessor
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.GodComponent;
import com.sk89q.util.yaml.YAMLFormat;
import com.sk89q.util.yaml.YAMLProcessor;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.blacklist.Blacklist;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ConfigurationManager {
    private static final String CONFIG_HEADER = "#\r\n# WorldGuard's main configuration file\r\n#\r\n# This is the global configuration file. Anything placed into here will\r\n# be applied to all worlds. However, each world has its own configuration\r\n# file to allow you to replace most settings in here for that world only.\r\n#\r\n# About editing this file:\r\n# - DO NOT USE TABS. You MUST use spaces or Bukkit will complain. If\r\n#   you use an editor like Notepad++ (recommended for Windows users), you\r\n#   must configure it to \"replace tabs with spaces.\" In Notepad++, this can\r\n#   be changed in Settings > Preferences > Language Menu.\r\n# - Don't get rid of the indents. They are indented so some entries are\r\n#   in categories (like \"enforce-single-session\" is in the \"protection\"\r\n#   category.\r\n# - If you want to check the format of this file before putting it\r\n#   into WorldGuard, paste it into http://yaml-online-parser.appspot.com/\r\n#   and see if it gives \"ERROR:\".\r\n# - Lines starting with # are comments and so they are ignored.\r\n#\r\n";
    private WorldGuardPlugin plugin;
    private ConcurrentMap<String, WorldConfiguration> worlds;
    private YAMLProcessor config;
    @Deprecated
    private Set<String> hasGodMode = new HashSet<String>();
    private Set<String> hasAmphibious = new HashSet<String>();
    private boolean hasCommandBookGodMode = false;
    public boolean useRegionsScheduler;
    public boolean useRegionsCreatureSpawnEvent;
    public boolean activityHaltToggle = false;
    public boolean autoGodMode;
    public boolean usePlayerMove;
    public boolean usePlayerTeleports;
    public boolean deopOnJoin;
    public boolean blockInGameOp;
    public Map<String, String> hostKeys = new HashMap<String, String>();
    public boolean useSqlDatabase = false;
    public String sqlDsn;
    public String sqlUsername;
    public String sqlPassword;

    public ConfigurationManager(WorldGuardPlugin plugin) {
        this.plugin = plugin;
        this.worlds = new ConcurrentHashMap<String, WorldConfiguration>();
    }

    public void load() {
        this.plugin.createDefaultConfiguration(new File(this.plugin.getDataFolder(), "config.yml"), "config.yml");
        this.config = new YAMLProcessor(new File(this.plugin.getDataFolder(), "config.yml"), true, YAMLFormat.EXTENDED);
        try {
            this.config.load();
        }
        catch (IOException e) {
            this.plugin.getLogger().severe("Error reading configuration for global config: ");
            e.printStackTrace();
        }
        this.config.removeProperty("suppress-tick-sync-warnings");
        this.useRegionsScheduler = this.config.getBoolean("regions.use-scheduler", true);
        this.useRegionsCreatureSpawnEvent = this.config.getBoolean("regions.use-creature-spawn-event", true);
        this.autoGodMode = this.config.getBoolean("auto-invincible", this.config.getBoolean("auto-invincible-permission", false));
        this.config.removeProperty("auto-invincible-permission");
        this.usePlayerMove = this.config.getBoolean("use-player-move-event", true);
        this.usePlayerTeleports = this.config.getBoolean("use-player-teleports", true);
        this.deopOnJoin = this.config.getBoolean("security.deop-everyone-on-join", false);
        this.blockInGameOp = this.config.getBoolean("security.block-in-game-op-command", false);
        this.hostKeys = new HashMap<String, String>();
        Object hostKeysRaw = this.config.getProperty("host-keys");
        if (hostKeysRaw == null || !(hostKeysRaw instanceof Map)) {
            this.config.setProperty("host-keys", new HashMap());
        } else {
            for (Map.Entry entry : ((Map)hostKeysRaw).entrySet()) {
                String key = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                this.hostKeys.put(key.toLowerCase(), value);
            }
        }
        this.useSqlDatabase = this.config.getBoolean("regions.sql.use", false);
        this.sqlDsn = this.config.getString("regions.sql.dsn", "jdbc:mysql://localhost/worldguard");
        this.sqlUsername = this.config.getString("regions.sql.username", "worldguard");
        this.sqlPassword = this.config.getString("regions.sql.password", "worldguard");
        for (World world : this.plugin.getServer().getWorlds()) {
            this.get(world);
        }
        this.config.setHeader(CONFIG_HEADER);
        if (!this.config.save()) {
            this.plugin.getLogger().severe("Error saving configuration!");
        }
    }

    public void unload() {
        this.worlds.clear();
    }

    public WorldConfiguration get(World world) {
        String worldName = world.getName();
        WorldConfiguration config = (WorldConfiguration)this.worlds.get(worldName);
        WorldConfiguration newConfig = null;
        while (config == null) {
            if (newConfig == null) {
                newConfig = new WorldConfiguration(this.plugin, worldName, this.config);
            }
            this.worlds.putIfAbsent(world.getName(), newConfig);
            config = (WorldConfiguration)this.worlds.get(world.getName());
        }
        return config;
    }

    public void forgetPlayer(LocalPlayer player) {
        for (Map.Entry entry : this.worlds.entrySet()) {
            Blacklist bl = ((WorldConfiguration)entry.getValue()).getBlacklist();
            if (bl == null) continue;
            bl.forgetPlayer(player);
        }
        this.hasGodMode.remove(player.getName());
        this.hasAmphibious.remove(player.getName());
    }

    @Deprecated
    public void enableGodMode(Player player) {
        this.hasGodMode.add(player.getName());
    }

    @Deprecated
    public void disableGodMode(Player player) {
        this.hasGodMode.remove(player.getName());
    }

    public boolean hasGodMode(Player player) {
        GodComponent god;
        if (this.hasCommandBookGodMode && (god = CommandBook.inst().getComponentManager().getComponent(GodComponent.class)) != null) {
            return god.hasGodMode(player);
        }
        return this.hasGodMode.contains(player.getName());
    }

    public void enableAmphibiousMode(Player player) {
        this.hasAmphibious.add(player.getName());
    }

    public void disableAmphibiousMode(Player player) {
        this.hasAmphibious.remove(player.getName());
    }

    public boolean hasAmphibiousMode(Player player) {
        return this.hasAmphibious.contains(player.getName());
    }

    public void updateCommandBookGodMode() {
        try {
            if (this.plugin.getServer().getPluginManager().isPluginEnabled("CommandBook")) {
                Class.forName("com.sk89q.commandbook.GodComponent");
                this.hasCommandBookGodMode = true;
                return;
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        this.hasCommandBookGodMode = false;
    }

    public boolean hasCommandBookGodMode() {
        return this.hasCommandBookGodMode;
    }
}

