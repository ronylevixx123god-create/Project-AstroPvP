/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.BlockVector2D
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.protection.regions;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.protection.UnsupportedIntersectionException;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.List;

public class ProtectedPolygonalRegion
extends ProtectedRegion {
    protected List<BlockVector2D> points;
    protected int minY;
    protected int maxY;

    public ProtectedPolygonalRegion(String id, List<BlockVector2D> points, int minY, int maxY) {
        super(id);
        this.points = points;
        this.setMinMaxPoints(points, minY, maxY);
        this.minY = this.min.getBlockY();
        this.maxY = this.max.getBlockY();
    }

    private void setMinMaxPoints(List<BlockVector2D> points2D, int minY, int maxY) {
        ArrayList<Vector> points = new ArrayList<Vector>();
        int y = minY;
        for (BlockVector2D point2D : points2D) {
            points.add(new Vector(point2D.getBlockX(), y, point2D.getBlockZ()));
            y = maxY;
        }
        this.setMinMaxPoints(points);
    }

    @Override
    public List<BlockVector2D> getPoints() {
        return this.points;
    }

    @Override
    public boolean contains(Vector pt) {
        int targetX = pt.getBlockX();
        int targetY = pt.getBlockY();
        int targetZ = pt.getBlockZ();
        if (targetY < this.minY || targetY > this.maxY) {
            return false;
        }
        if (targetX < this.min.getBlockX() || targetX > this.max.getBlockX() || targetZ < this.min.getBlockZ() || targetZ > this.max.getBlockZ()) {
            return false;
        }
        boolean inside = false;
        int npoints = this.points.size();
        int xOld = this.points.get(npoints - 1).getBlockX();
        int zOld = this.points.get(npoints - 1).getBlockZ();
        for (int i = 0; i < npoints; ++i) {
            int z2;
            int z1;
            int x2;
            int x1;
            int xNew = this.points.get(i).getBlockX();
            int zNew = this.points.get(i).getBlockZ();
            if (xNew == targetX && zNew == targetZ) {
                return true;
            }
            if (xNew > xOld) {
                x1 = xOld;
                x2 = xNew;
                z1 = zOld;
                z2 = zNew;
            } else {
                x1 = xNew;
                x2 = xOld;
                z1 = zNew;
                z2 = zOld;
            }
            if (x1 <= targetX && targetX <= x2) {
                long crossproduct = ((long)targetZ - (long)z1) * (long)(x2 - x1) - ((long)z2 - (long)z1) * (long)(targetX - x1);
                if (crossproduct == 0L) {
                    if (z1 <= targetZ == targetZ <= z2) {
                        return true;
                    }
                } else if (crossproduct < 0L && x1 != targetX) {
                    inside = !inside;
                }
            }
            xOld = xNew;
            zOld = zNew;
        }
        return inside;
    }

    @Override
    public List<ProtectedRegion> getIntersectingRegions(List<ProtectedRegion> regions) throws UnsupportedIntersectionException {
        ArrayList<ProtectedRegion> intersectingRegions = new ArrayList<ProtectedRegion>();
        for (ProtectedRegion region : regions) {
            if (!this.intersectsBoundingBox(region)) continue;
            if (region instanceof ProtectedPolygonalRegion || region instanceof ProtectedCuboidRegion) {
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
        return "polygon";
    }

    @Override
    public int volume() {
        int volume = 0;
        return volume;
    }
}

