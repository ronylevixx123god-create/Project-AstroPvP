/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.protection.managers;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.databases.ProtectionDatabase;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegionMBRConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.khelekore.prtree.MBRConverter;
import org.khelekore.prtree.PRTree;
import org.khelekore.prtree.SimpleMBR;

public class PRTreeRegionManager
extends RegionManager {
    private static final int BRANCH_FACTOR = 30;
    private Map<String, ProtectedRegion> regions;
    private MBRConverter<ProtectedRegion> converter = new ProtectedRegionMBRConverter();
    private PRTree<ProtectedRegion> tree;

    public PRTreeRegionManager(ProtectionDatabase regionLoader) {
        super(regionLoader);
        this.regions = new TreeMap<String, ProtectedRegion>();
        this.tree = new PRTree<ProtectedRegion>(this.converter, 30);
    }

    @Override
    public Map<String, ProtectedRegion> getRegions() {
        return this.regions;
    }

    @Override
    public void setRegions(Map<String, ProtectedRegion> regions) {
        this.regions = new TreeMap<String, ProtectedRegion>(regions);
        this.tree = new PRTree<ProtectedRegion>(this.converter, 30);
        this.tree.load(regions.values());
    }

    @Override
    public void addRegion(ProtectedRegion region) {
        this.regions.put(region.getId().toLowerCase(), region);
        this.tree = new PRTree<ProtectedRegion>(this.converter, 30);
        this.tree.load(this.regions.values());
    }

    @Override
    public boolean hasRegion(String id) {
        return this.regions.containsKey(id.toLowerCase());
    }

    @Override
    public void removeRegion(String id) {
        ProtectedRegion region = this.regions.get(id.toLowerCase());
        this.regions.remove(id.toLowerCase());
        if (region != null) {
            ArrayList<String> removeRegions = new ArrayList<String>();
            for (ProtectedRegion curRegion : this.regions.values()) {
                if (curRegion.getParent() != region) continue;
                removeRegions.add(curRegion.getId().toLowerCase());
            }
            for (String remId : removeRegions) {
                this.removeRegion(remId);
            }
        }
        this.tree = new PRTree<ProtectedRegion>(this.converter, 30);
        this.tree.load(this.regions.values());
    }

    @Override
    public ApplicableRegionSet getApplicableRegions(Vector pt) {
        pt = pt.floor();
        ArrayList<ProtectedRegion> appRegions = new ArrayList<ProtectedRegion>();
        SimpleMBR pointMBR = new SimpleMBR(pt.getX(), pt.getX(), pt.getY(), pt.getY(), pt.getZ(), pt.getZ());
        for (ProtectedRegion region : this.tree.find(pointMBR)) {
            if (!region.contains(pt) || appRegions.contains(region)) continue;
            appRegions.add(region);
            for (ProtectedRegion parent = region.getParent(); parent != null; parent = parent.getParent()) {
                if (appRegions.contains(parent)) continue;
                appRegions.add(parent);
            }
        }
        Collections.sort(appRegions);
        return new ApplicableRegionSet(appRegions, this.regions.get("__global__"));
    }

    @Override
    public ApplicableRegionSet getApplicableRegions(ProtectedRegion checkRegion) {
        List<ProtectedRegion> intersectRegions;
        ArrayList<ProtectedRegion> appRegions = new ArrayList<ProtectedRegion>();
        appRegions.addAll(this.regions.values());
        try {
            intersectRegions = checkRegion.getIntersectingRegions(appRegions);
        }
        catch (Exception e) {
            intersectRegions = new ArrayList<ProtectedRegion>();
        }
        return new ApplicableRegionSet(intersectRegions, this.regions.get("__global__"));
    }

    @Override
    public List<String> getApplicableRegionsIDs(Vector pt) {
        pt = pt.floor();
        ArrayList<String> applicable = new ArrayList<String>();
        SimpleMBR pointMBR = new SimpleMBR(pt.getX(), pt.getX(), pt.getY(), pt.getY(), pt.getZ(), pt.getZ());
        for (ProtectedRegion region : this.tree.find(pointMBR)) {
            if (!region.contains(pt) || applicable.contains(region.getId())) continue;
            applicable.add(region.getId());
            for (ProtectedRegion parent = region.getParent(); parent != null; parent = parent.getParent()) {
                if (applicable.contains(parent.getId())) continue;
                applicable.add(parent.getId());
            }
        }
        return applicable;
    }

    @Override
    public boolean overlapsUnownedRegion(ProtectedRegion checkRegion, LocalPlayer player) {
        List<ProtectedRegion> intersectRegions;
        ArrayList<ProtectedRegion> appRegions = new ArrayList<ProtectedRegion>();
        for (ProtectedRegion other : this.regions.values()) {
            if (other.getOwners().contains(player)) continue;
            appRegions.add(other);
        }
        try {
            intersectRegions = checkRegion.getIntersectingRegions(appRegions);
        }
        catch (Exception e) {
            intersectRegions = new ArrayList<ProtectedRegion>();
        }
        return intersectRegions.size() > 0;
    }

    @Override
    public int size() {
        return this.regions.size();
    }

    @Override
    public int getRegionCountOfPlayer(LocalPlayer player) {
        int count = 0;
        for (Map.Entry<String, ProtectedRegion> entry : this.regions.entrySet()) {
            if (!entry.getValue().getOwners().contains(player)) continue;
            ++count;
        }
        return count;
    }
}

