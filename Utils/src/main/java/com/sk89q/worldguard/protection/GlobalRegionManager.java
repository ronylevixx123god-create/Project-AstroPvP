/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.BukkitPlayer;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.databases.AbstractProtectionDatabase;
import com.sk89q.worldguard.protection.databases.MySQLDatabase;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.databases.YAMLDatabase;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.PRTreeRegionManager;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class GlobalRegionManager {
    private WorldGuardPlugin plugin;
    private ConfigurationManager config;
    private ConcurrentHashMap<String, RegionManager> managers;
    private HashMap<String, Long> lastModified;

    public GlobalRegionManager(WorldGuardPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getGlobalStateManager();
        this.managers = new ConcurrentHashMap();
        this.lastModified = new HashMap();
    }

    public void unload() {
        this.managers.clear();
        this.lastModified.clear();
    }

    protected File getPath(String name) {
        return new File(this.plugin.getDataFolder(), "worlds" + File.separator + name + File.separator + "regions.yml");
    }

    public void unload(String name) {
        RegionManager manager = this.managers.remove(name);
        if (manager != null) {
            this.lastModified.remove(name);
        }
    }

    public void unloadAll() {
        this.managers.clear();
        this.lastModified.clear();
    }

    public RegionManager load(World world) {
        RegionManager manager = this.create(world);
        this.managers.put(world.getName(), manager);
        return manager;
    }

    public RegionManager create(World world) {
        String name = world.getName();
        boolean sql = this.config.useSqlDatabase;
        File file = null;
        try {
            AbstractProtectionDatabase database;
            if (!sql) {
                file = this.getPath(name);
                database = new YAMLDatabase(file, this.plugin.getLogger());
                this.lastModified.put(name, file.lastModified());
            } else {
                database = new MySQLDatabase(this.config, name, this.plugin.getLogger());
            }
            PRTreeRegionManager manager = new PRTreeRegionManager(database);
            manager.load();
            if (this.plugin.getGlobalStateManager().get((World)world).summaryOnStart) {
                this.plugin.getLogger().info(((RegionManager)manager).getRegions().size() + " regions loaded for '" + name + "'");
            }
            return manager;
        }
        catch (ProtectionDatabaseException e) {
            String logStr = "Failed to load regions from ";
            logStr = sql ? logStr + "SQL Database <" + this.config.sqlDsn + "> " : logStr + "file \"" + file + "\" ";
            this.plugin.getLogger().log(Level.SEVERE, logStr + " : " + e.getMessage());
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Error loading regions for world \"" + name + "\": " + e.toString() + "\n\t" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void preload() {
        for (World world : this.plugin.getServer().getWorlds()) {
            this.load(world);
        }
    }

    public void reloadChanged() {
        if (this.config.useSqlDatabase) {
            return;
        }
        for (String name : this.managers.keySet()) {
            File file = this.getPath(name);
            Long oldDate = this.lastModified.get(name);
            if (oldDate == null) {
                oldDate = 0L;
            }
            try {
                World world;
                if (file.lastModified() <= oldDate || (world = this.plugin.getServer().getWorld(name)) == null) continue;
                this.load(world);
            }
            catch (Exception exception) {}
        }
    }

    public RegionManager get(World world) {
        RegionManager manager = this.managers.get(world.getName());
        RegionManager newManager = null;
        while (manager == null) {
            if (newManager == null) {
                newManager = this.create(world);
            }
            this.managers.putIfAbsent(world.getName(), newManager);
            manager = this.managers.get(world.getName());
        }
        return manager;
    }

    public boolean hasBypass(LocalPlayer player, World world) {
        if (player instanceof BukkitPlayer) {
            return ((BukkitPlayer)player).getPlayer().isOp();
        }
        return player.hasPermission("worldguard.region.bypass." + world.getName());
    }

    public boolean hasBypass(Player player, World world) {
        return player.isOp();
    }

    public boolean canBuild(Player player, Block block) {
        return this.canBuild(player, block.getLocation());
    }

    public boolean canBuild(Player player, Location loc) {
        World world = loc.getWorld();
        WorldConfiguration worldConfig = this.config.get(world);
        if (!worldConfig.useRegions) {
            return true;
        }
        if (!player.isOp()) {
            Object o;
            RegionManager mgr = this.get(world);
            if (mgr.getApplicableRegions(loc).size() > 0 && !mgr.getApplicableRegions(loc).canBuild(this.plugin.wrapPlayer(player))) {
                return false;
            }
            ProtectedRegion global = mgr.getRegion("__global__");
            if (global != null && (o = mgr.getRegion("__global__").getFlag(DefaultFlag.BUILD)) != null && o == StateFlag.State.DENY) {
                return false;
            }
        }
        return true;
    }

    public boolean canConstruct(Player player, Block block) {
        return this.canConstruct(player, block.getLocation());
    }

    public boolean canConstruct(Player player, Location loc) {
        World world = loc.getWorld();
        WorldConfiguration worldConfig = this.config.get(world);
        if (!worldConfig.useRegions) {
            return true;
        }
        LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
        if (!this.hasBypass(player, world)) {
            RegionManager mgr = this.get(world);
            ApplicableRegionSet applicableRegions = mgr.getApplicableRegions(BukkitUtil.toVector(loc));
            if (!applicableRegions.canBuild(localPlayer)) {
                return false;
            }
            if (!applicableRegions.canConstruct(localPlayer)) {
                return false;
            }
        }
        return true;
    }

    public boolean allows(StateFlag flag, Location loc) {
        return this.allows(flag, loc, null);
    }

    public boolean allows(StateFlag flag, Location loc, LocalPlayer player) {
        World world = loc.getWorld();
        WorldConfiguration worldConfig = this.config.get(world);
        if (!worldConfig.useRegions) {
            return true;
        }
        RegionManager mgr = this.get(world);
        return mgr.getApplicableRegions(BukkitUtil.toVector(loc)).allows(flag, player);
    }
}

