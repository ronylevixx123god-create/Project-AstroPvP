/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.databases;

import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Map;

public interface ProtectionDatabase {
    public void load() throws ProtectionDatabaseException;

    public void save() throws ProtectionDatabaseException;

    public void load(RegionManager var1) throws ProtectionDatabaseException;

    public void save(RegionManager var1) throws ProtectionDatabaseException;

    public Map<String, ProtectedRegion> getRegions();

    public void setRegions(Map<String, ProtectedRegion> var1);
}

