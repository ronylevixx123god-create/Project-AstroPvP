/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldedit.bukkit.BukkitUtil
 */
package com.sk89q.worldguard.protection.managers;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.databases.ProtectionDatabase;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;

public abstract class RegionManager {
    protected ProtectionDatabase loader;

    public RegionManager(ProtectionDatabase loader) {
        this.loader = loader;
    }

    public void load() throws ProtectionDatabaseException {
        this.loader.load(this);
    }

    public void save() throws ProtectionDatabaseException {
        this.loader.save(this);
    }

    public abstract Map<String, ProtectedRegion> getRegions();

    public abstract void setRegions(Map<String, ProtectedRegion> var1);

    public abstract void addRegion(ProtectedRegion var1);

    public abstract boolean hasRegion(String var1);

    public ProtectedRegion getRegion(String id) {
        if (id.startsWith("#")) {
            int index;
            try {
                index = Integer.parseInt(id.substring(1)) - 1;
            }
            catch (NumberFormatException e) {
                return null;
            }
            for (ProtectedRegion region : this.getRegions().values()) {
                if (index == 0) {
                    return region;
                }
                --index;
            }
            return null;
        }
        return this.getRegionExact(id);
    }

    public ProtectedRegion getRegionExact(String id) {
        return this.getRegions().get(id.toLowerCase());
    }

    public abstract void removeRegion(String var1);

    public ApplicableRegionSet getApplicableRegions(Location loc) {
        return this.getApplicableRegions(BukkitUtil.toVector((Location)loc));
    }

    public abstract ApplicableRegionSet getApplicableRegions(Vector var1);

    public abstract ApplicableRegionSet getApplicableRegions(ProtectedRegion var1);

    public abstract List<String> getApplicableRegionsIDs(Vector var1);

    public abstract boolean overlapsUnownedRegion(ProtectedRegion var1, LocalPlayer var2);

    public abstract int size();

    public abstract int getRegionCountOfPlayer(LocalPlayer var1);
}

