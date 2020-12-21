/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.databases.migrators;

import com.sk89q.worldguard.protection.databases.migrators.MigrationException;

public interface DatabaseMigrator {
    public void migrate() throws MigrationException;
}

