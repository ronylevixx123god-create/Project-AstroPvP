/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.databases.migrators;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.databases.MySQLDatabase;
import com.sk89q.worldguard.protection.databases.ProtectionDatabase;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.databases.YAMLDatabase;
import com.sk89q.worldguard.protection.databases.migrators.AbstractDatabaseMigrator;
import com.sk89q.worldguard.protection.databases.migrators.MigrationException;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class YAMLToMySQLMigrator
extends AbstractDatabaseMigrator {
    private WorldGuardPlugin plugin;
    private HashMap<String, File> regionYamlFiles;

    public YAMLToMySQLMigrator(WorldGuardPlugin plugin) {
        File[] files;
        this.plugin = plugin;
        this.regionYamlFiles = new HashMap();
        for (File item : files = new File(plugin.getDataFolder(), "worlds" + File.separator).listFiles()) {
            if (!item.isDirectory()) continue;
            for (File subItem : item.listFiles()) {
                if (!subItem.getName().equals("regions.yml")) continue;
                this.regionYamlFiles.put(item.getName(), subItem);
            }
        }
    }

    @Override
    protected Set<String> getWorldsFromOld() {
        return this.regionYamlFiles.keySet();
    }

    @Override
    protected Map<String, ProtectedRegion> getRegionsForWorldFromOld(String world) throws MigrationException {
        YAMLDatabase oldDatabase;
        try {
            oldDatabase = new YAMLDatabase(this.regionYamlFiles.get(world), this.plugin.getLogger());
            oldDatabase.load();
        }
        catch (FileNotFoundException e) {
            throw new MigrationException(e);
        }
        catch (ProtectionDatabaseException e) {
            throw new MigrationException(e);
        }
        return oldDatabase.getRegions();
    }

    @Override
    protected ProtectionDatabase getNewWorldStorage(String world) throws MigrationException {
        try {
            return new MySQLDatabase(this.plugin.getGlobalStateManager(), world, this.plugin.getLogger());
        }
        catch (ProtectionDatabaseException e) {
            throw new MigrationException(e);
        }
    }
}

