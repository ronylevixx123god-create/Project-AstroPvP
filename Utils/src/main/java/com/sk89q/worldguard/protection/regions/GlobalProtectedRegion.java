/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldedit.BlockVector2D
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.protection.regions;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.protection.UnsupportedIntersectionException;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.List;

public class GlobalProtectedRegion
extends ProtectedRegion {
    public GlobalProtectedRegion(String id) {
        super(id);
        this.min = new BlockVector(0, 0, 0);
        this.max = new BlockVector(0, 0, 0);
    }

    @Override
    public List<BlockVector2D> getPoints() {
        ArrayList<BlockVector2D> pts = new ArrayList<BlockVector2D>();
        pts.add(new BlockVector2D(this.min.getBlockX(), this.min.getBlockZ()));
        return pts;
    }

    @Override
    public int volume() {
        return 0;
    }

    @Override
    public boolean contains(Vector pt) {
        return false;
    }

    @Override
    public String getTypeName() {
        return "global";
    }

    @Override
    public List<ProtectedRegion> getIntersectingRegions(List<ProtectedRegion> regions) throws UnsupportedIntersectionException {
        return new ArrayList<ProtectedRegion>();
    }
}

