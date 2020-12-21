/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.GlobalRegionManager;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.util.LogListBlock;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public class ReportWriter {
    private static final SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd kk:mm Z");
    private Date date = new Date();
    private StringBuilder output = new StringBuilder();

    public ReportWriter(WorldGuardPlugin plugin) {
        this.appendReportHeader(plugin);
        this.appendServerInformation(plugin.getServer());
        this.appendPluginInformation(plugin.getServer().getPluginManager().getPlugins());
        this.appendWorldInformation(plugin.getServer().getWorlds());
        this.appendGlobalConfiguration(plugin.getGlobalStateManager());
        this.appendWorldConfigurations(plugin, plugin.getServer().getWorlds(), plugin.getGlobalRegionManager(), plugin.getGlobalStateManager());
        this.appendln("-------------");
        this.appendln("END OF REPORT");
        this.appendln();
    }

    protected static String repeat(String str, int n) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            sb.append(str);
        }
        return sb.toString();
    }

    protected void appendln(String text) {
        this.output.append(text);
        this.output.append("\r\n");
    }

    protected void appendln(String text, Object ... args) {
        this.output.append(String.format(text, args));
        this.output.append("\r\n");
    }

    protected void append(LogListBlock log) {
        this.output.append(log.toString());
    }

    protected void appendln() {
        this.output.append("\r\n");
    }

    protected void appendHeader(String text) {
        String rule = ReportWriter.repeat("-", text.length());
        this.output.append(rule);
        this.output.append("\r\n");
        this.appendln(text);
        this.output.append(rule);
        this.output.append("\r\n");
        this.appendln();
    }

    private void appendReportHeader(WorldGuardPlugin plugin) {
        this.appendln("WorldGuard Configuration Report");
        this.appendln("Generated " + dateFmt.format(this.date));
        this.appendln();
        this.appendln("Version: " + plugin.getDescription().getVersion());
        this.appendln();
    }

    private void appendGlobalConfiguration(ConfigurationManager config) {
        this.appendHeader("Global Configuration");
        LogListBlock log = new LogListBlock();
        LogListBlock configLog = log.putChild("Configuration");
        Class<?> cls = config.getClass();
        for (Field field : cls.getFields()) {
            try {
                if (field.getName().equalsIgnoreCase("CONFIG_HEADER")) continue;
                Object val = field.get(config);
                configLog.put(field.getName(), val);
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        }
        this.append(log);
        this.appendln();
    }

    private void appendServerInformation(Server server) {
        this.appendHeader("Server Information");
        LogListBlock log = new LogListBlock();
        Runtime runtime = Runtime.getRuntime();
        log.put("Java", "%s %s (%s)", System.getProperty("java.vendor"), System.getProperty("java.version"), System.getProperty("java.vendor.url"));
        log.put("Operating system", "%s %s (%s)", System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"));
        log.put("Available processors", runtime.availableProcessors());
        log.put("Free memory", runtime.freeMemory() / 1024L / 1024L + " MB");
        log.put("Max memory", runtime.maxMemory() / 1024L / 1024L + " MB");
        log.put("Total memory", runtime.totalMemory() / 1024L / 1024L + " MB");
        log.put("Server ID", server.getServerId());
        log.put("Server name", server.getServerName());
        log.put("Implementation", server.getVersion());
        log.put("Player count", "%d/%d", server.getOnlinePlayers().length, server.getMaxPlayers());
        this.append(log);
        this.appendln();
    }

    private void appendPluginInformation(Plugin[] plugins) {
        this.appendHeader("Plugins (" + plugins.length + ")");
        LogListBlock log = new LogListBlock();
        for (Plugin plugin : plugins) {
            log.put(plugin.getDescription().getName(), plugin.getDescription().getVersion());
        }
        this.append(log);
        this.appendln();
    }

    private void appendWorldInformation(List<World> worlds) {
        this.appendHeader("Worlds");
        LogListBlock log = new LogListBlock();
        int i = 0;
        for (World world : worlds) {
            int loadedChunkCount = world.getLoadedChunks().length;
            LogListBlock worldLog = log.putChild(world.getName() + " (" + i + ")");
            LogListBlock infoLog = worldLog.putChild("Information");
            LogListBlock entitiesLog = worldLog.putChild("Entities");
            infoLog.put("Seed", world.getSeed());
            infoLog.put("Environment", world.getEnvironment().toString());
            infoLog.put("Player count", world.getPlayers().size());
            infoLog.put("Entity count", world.getEntities().size());
            infoLog.put("Loaded chunk count", loadedChunkCount);
            infoLog.put("Spawn location", world.getSpawnLocation());
            infoLog.put("Raw time", world.getFullTime());
            HashMap entityCounts = new HashMap();
            for (Entity entity : world.getEntities()) {
                Class<?> cls = entity.getClass();
                if (entityCounts.containsKey(cls)) {
                    entityCounts.put(cls, (Integer)entityCounts.get(cls) + 1);
                    continue;
                }
                entityCounts.put(cls, 1);
            }
            for (Map.Entry entry : entityCounts.entrySet()) {
                entitiesLog.put(((Class)entry.getKey()).getSimpleName(), "%d [%f/chunk]", entry.getValue(), Float.valueOf((float)((double)((Integer)entry.getValue()).intValue() / (double)loadedChunkCount)));
            }
            ++i;
        }
        this.append(log);
        this.appendln();
    }

    private void appendWorldConfigurations(WorldGuardPlugin plugin, List<World> worlds, GlobalRegionManager regionMgr, ConfigurationManager mgr) {
        this.appendHeader("World Configurations");
        LogListBlock log = new LogListBlock();
        int i = 0;
        for (World world : worlds) {
            LogListBlock worldLog = log.putChild(world.getName() + " (" + i + ")");
            LogListBlock infoLog = worldLog.putChild("Information");
            LogListBlock configLog = worldLog.putChild("Configuration");
            LogListBlock blacklistLog = worldLog.putChild("Blacklist");
            LogListBlock regionsLog = worldLog.putChild("Region manager");
            infoLog.put("Configuration file", new File(plugin.getDataFolder(), "worlds/" + world.getName() + "/config.yml").getAbsoluteFile());
            infoLog.put("Blacklist file", new File(plugin.getDataFolder(), "worlds/" + world.getName() + "/blacklist.txt").getAbsoluteFile());
            infoLog.put("Regions file", new File(plugin.getDataFolder(), "worlds/" + world.getName() + "/regions.yml").getAbsoluteFile());
            WorldConfiguration config = mgr.get(world);
            Class<?> cls = config.getClass();
            for (Field field : cls.getFields()) {
                try {
                    Object val = field.get(config);
                    configLog.put(field.getName(), String.valueOf(val));
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException illegalAccessException) {
                    // empty catch block
                }
            }
            if (config.getBlacklist() == null) {
                blacklistLog.put("State", "DISABLED");
            } else {
                blacklistLog.put("State", "Enabled");
                blacklistLog.put("Number of items", config.getBlacklist().getItemCount());
                blacklistLog.put("Is whitelist", config.getBlacklist().isWhitelist());
            }
            RegionManager worldRegions = regionMgr.get(world);
            regionsLog.put("Type", worldRegions.getClass().getCanonicalName());
            regionsLog.put("Number of regions", worldRegions.getRegions().size());
            LogListBlock globalRegionLog = regionsLog.putChild("Global region");
            ProtectedRegion globalRegion = worldRegions.getRegion("__global__");
            if (globalRegion == null) {
                globalRegionLog.put("Status", "UNDEFINED");
                continue;
            }
            for (Flag<?> flag : DefaultFlag.getFlags()) {
                if (!(flag instanceof StateFlag)) continue;
                globalRegionLog.put(flag.getName(), globalRegion.getFlag(flag));
            }
        }
        this.append(log);
        this.appendln();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void write(File file) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(this.output.toString());
            out.close();
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    public String toString() {
        return this.output.toString();
    }
}

