/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.databases.migrators;

import com.sk89q.worldguard.bukkit.ConfigurationManager;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MySQLToYAMLMigrator
extends AbstractDatabaseMigrator {
    private WorldGuardPlugin plugin;
    private Set<String> worlds;

    public MySQLToYAMLMigrator(WorldGuardPlugin plugin) throws MigrationException {
        this.plugin = plugin;
        this.worlds = new HashSet<String>();
        ConfigurationManager config = plugin.getGlobalStateManager();
        try {
            Connection conn = DriverManager.getConnection(config.sqlDsn, config.sqlUsername, config.sqlPassword);
            ResultSet worlds = conn.prepareStatement("SELECT `name` FROM `world`;").executeQuery();
            while (worlds.next()) {
                this.worlds.add(worlds.getString(1));
            }
            conn.close();
        }
        catch (SQLException e) {
            throw new MigrationException(e);
        }
    }

    @Override
    protected Set<String> getWorldsFromOld() {
        return this.worlds;
    }

    @Override
    protected Map<String, ProtectedRegion> getRegionsForWorldFromOld(String world) throws MigrationException {
        MySQLDatabase oldDatabase;
        try {
            oldDatabase = new MySQLDatabase(this.plugin.getGlobalStateManager(), world, this.plugin.getLogger());
            oldDatabase.load();
        }
        catch (ProtectionDatabaseException e) {
            throw new MigrationException(e);
        }
        return oldDatabase.getRegions();
    }

    @Override
    protected ProtectionDatabase getNewWorldStorage(String world) throws MigrationException {
        try {
            File file = new File(this.plugin.getDataFolder(), "worlds" + File.separator + world + File.separator + "regions.yml");
            return new YAMLDatabase(file, this.plugin.getLogger());
        }
        catch (FileNotFoundException e) {
            throw new MigrationException(e);
        }
        catch (ProtectionDatabaseException e) {
            throw new MigrationException(e);
        }
    }
}

