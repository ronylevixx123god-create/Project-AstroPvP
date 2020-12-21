/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.databases;

import com.sk89q.worldguard.protection.databases.ProtectionDatabase;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.managers.RegionManager;

public abstract class AbstractProtectionDatabase
implements ProtectionDatabase {
    @Override
    public void load(RegionManager manager) throws ProtectionDatabaseException {
        this.load();
        manager.setRegions(this.getRegions());
    }

    @Override
    public void save(RegionManager manager) throws ProtectionDatabaseException {
        this.setRegions(manager.getRegions());
        this.save();
    }
}

