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
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.List;

public class ProtectedCuboidRegion
extends ProtectedRegion {
    public ProtectedCuboidRegion(String id, BlockVector pt1, BlockVector pt2) {
        super(id);
        this.setMinMaxPoints(pt1, pt2);
    }

    private void setMinMaxPoints(BlockVector pt1, BlockVector pt2) {
        ArrayList<Vector> points = new ArrayList<Vector>();
        points.add((Vector)pt1);
        points.add((Vector)pt2);
        this.setMinMaxPoints(points);
    }

    public void setMinimumPoint(BlockVector pt) {
        this.setMinMaxPoints(pt, this.max);
    }

    public void setMaximumPoint(BlockVector pt) {
        this.setMinMaxPoints(this.min, pt);
    }

    @Override
    public List<BlockVector2D> getPoints() {
        ArrayList<BlockVector2D> pts = new ArrayList<BlockVector2D>();
        int x1 = this.min.getBlockX();
        int x2 = this.max.getBlockX();
        int z1 = this.min.getBlockZ();
        int z2 = this.max.getBlockZ();
        pts.add(new BlockVector2D(x1, z1));
        pts.add(new BlockVector2D(x2, z1));
        pts.add(new BlockVector2D(x2, z2));
        pts.add(new BlockVector2D(x1, z2));
        return pts;
    }

    @Override
    public boolean contains(Vector pt) {
        double x = pt.getX();
        double y = pt.getY();
        double z = pt.getZ();
        return x >= (double)this.min.getBlockX() && x < (double)(this.max.getBlockX() + 1) && y >= (double)this.min.getBlockY() && y < (double)(this.max.getBlockY() + 1) && z >= (double)this.min.getBlockZ() && z < (double)(this.max.getBlockZ() + 1);
    }

    @Override
    public List<ProtectedRegion> getIntersectingRegions(List<ProtectedRegion> regions) throws UnsupportedIntersectionException {
        ArrayList<ProtectedRegion> intersectingRegions = new ArrayList<ProtectedRegion>();
        for (ProtectedRegion region : regions) {
            if (!this.intersectsBoundingBox(region)) continue;
            if (region instanceof ProtectedCuboidRegion) {
                intersectingRegions.add(region);
                continue;
            }
            if (region instanceof ProtectedPolygonalRegion) {
                if (!this.containsAny(region.getPoints()) && !region.containsAny(this.getPoints()) && !this.intersectsEdges(region)) continue;
                intersectingRegions.add(region);
                continue;
            }
            throw new UnsupportedOperationException("Not supported yet.");
        }
        return intersectingRegions;
    }

    @Override
    public String getTypeName() {
        return "cuboid";
    }

    @Override
    public int volume() {
        int xLength = this.max.getBlockX() - this.min.getBlockX() + 1;
        int yLength = this.max.getBlockY() - this.min.getBlockY() + 1;
        int zLength = this.max.getBlockZ() - this.min.getBlockZ() + 1;
        return xLength * yLength * zLength;
    }
}

