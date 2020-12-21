/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.databases.migrators;

import com.sk89q.worldguard.protection.databases.ProtectionDatabase;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.databases.migrators.DatabaseMigrator;
import com.sk89q.worldguard.protection.databases.migrators.MigrationException;
import com.sk89q.worldguard.protection.databases.migrators.MigratorKey;
import com.sk89q.worldguard.protection.databases.migrators.MySQLToYAMLMigrator;
import com.sk89q.worldguard.protection.databases.migrators.YAMLToMySQLMigrator;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractDatabaseMigrator
implements DatabaseMigrator {
    private static HashMap<MigratorKey, Class<? extends AbstractDatabaseMigrator>> migrators = new HashMap();

    public static Map<MigratorKey, Class<? extends AbstractDatabaseMigrator>> getMigrators() {
        if (!migrators.isEmpty()) {
            return migrators;
        }
        migrators.put(new MigratorKey("mysql", "yaml"), MySQLToYAMLMigrator.class);
        migrators.put(new MigratorKey("yaml", "mysql"), YAMLToMySQLMigrator.class);
        return migrators;
    }

    protected abstract Set<String> getWorldsFromOld() throws MigrationException;

    protected abstract Map<String, ProtectedRegion> getRegionsForWorldFromOld(String var1) throws MigrationException;

    protected abstract ProtectionDatabase getNewWorldStorage(String var1) throws MigrationException;

    @Override
    public void migrate() throws MigrationException {
        for (String world : this.getWorldsFromOld()) {
            ProtectionDatabase database = this.getNewWorldStorage(world);
            database.setRegions(this.getRegionsForWorldFromOld(world));
            try {
                database.save();
            }
            catch (ProtectionDatabaseException e) {
                throw new MigrationException(e);
            }
        }
    }
}

