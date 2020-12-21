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
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.UnsupportedIntersectionException;
import com.sk89q.worldguard.protection.flags.Flag;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class ProtectedRegion
implements Comparable<ProtectedRegion> {
    protected BlockVector min;
    protected BlockVector max;
    private static final Pattern idPattern = Pattern.compile("^[A-Za-z0-9_,'\\-\\+/]{1,}$");
    private String id;
    private int priority = 0;
    private ProtectedRegion parent;
    private DefaultDomain owners = new DefaultDomain();
    private DefaultDomain members = new DefaultDomain();
    private Map<Flag<?>, Object> flags = new HashMap();

    public ProtectedRegion(String id) {
        this.id = id;
    }

    protected void setMinMaxPoints(List<Vector> points) {
        int minX = points.get(0).getBlockX();
        int minY = points.get(0).getBlockY();
        int minZ = points.get(0).getBlockZ();
        int maxX = minX;
        int maxY = minY;
        int maxZ = minZ;
        for (Vector v : points) {
            int x = v.getBlockX();
            int y = v.getBlockY();
            int z = v.getBlockZ();
            if (x < minX) {
                minX = x;
            }
            if (y < minY) {
                minY = y;
            }
            if (z < minZ) {
                minZ = z;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (y > maxY) {
                maxY = y;
            }
            if (z <= maxZ) continue;
            maxZ = z;
        }
        this.min = new BlockVector(minX, minY, minZ);
        this.max = new BlockVector(maxX, maxY, maxZ);
    }

    public String getId() {
        return this.id;
    }

    public BlockVector getMinimumPoint() {
        return this.min;
    }

    public BlockVector getMaximumPoint() {
        return this.max;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ProtectedRegion getParent() {
        return this.parent;
    }

    public void setParent(ProtectedRegion parent) throws CircularInheritanceException {
        if (parent == null) {
            this.parent = null;
            return;
        }
        if (parent == this) {
            throw new CircularInheritanceException();
        }
        for (ProtectedRegion p = parent.getParent(); p != null; p = p.getParent()) {
            if (p != this) continue;
            throw new CircularInheritanceException();
        }
        this.parent = parent;
    }

    public DefaultDomain getOwners() {
        return this.owners;
    }

    public void setOwners(DefaultDomain owners) {
        this.owners = owners;
    }

    public DefaultDomain getMembers() {
        return this.members;
    }

    public void setMembers(DefaultDomain members) {
        this.members = members;
    }

    public boolean hasMembersOrOwners() {
        return this.owners.size() > 0 || this.members.size() > 0;
    }

    public boolean isOwner(LocalPlayer player) {
        if (this.owners.contains(player)) {
            return true;
        }
        for (ProtectedRegion curParent = this.getParent(); curParent != null; curParent = curParent.getParent()) {
            if (!curParent.getOwners().contains(player)) continue;
            return true;
        }
        return false;
    }

    public boolean isOwner(String playerName) {
        if (this.owners.contains(playerName)) {
            return true;
        }
        for (ProtectedRegion curParent = this.getParent(); curParent != null; curParent = curParent.getParent()) {
            if (!curParent.getOwners().contains(playerName)) continue;
            return true;
        }
        return false;
    }

    public boolean isMember(LocalPlayer player) {
        if (this.isOwner(player)) {
            return true;
        }
        if (this.members.contains(player)) {
            return true;
        }
        for (ProtectedRegion curParent = this.getParent(); curParent != null; curParent = curParent.getParent()) {
            if (!curParent.getMembers().contains(player)) continue;
            return true;
        }
        return false;
    }

    public boolean isMember(String playerName) {
        if (this.isOwner(playerName)) {
            return true;
        }
        if (this.members.contains(playerName)) {
            return true;
        }
        for (ProtectedRegion curParent = this.getParent(); curParent != null; curParent = curParent.getParent()) {
            if (!curParent.getMembers().contains(playerName)) continue;
            return true;
        }
        return false;
    }

    public boolean isMemberOnly(LocalPlayer player) {
        if (this.members.contains(player)) {
            return true;
        }
        for (ProtectedRegion curParent = this.getParent(); curParent != null; curParent = curParent.getParent()) {
            if (!curParent.getMembers().contains(player)) continue;
            return true;
        }
        return false;
    }

    public <T extends Flag<V>, V> V getFlag(T flag) {
        Object obj = this.flags.get(flag);
        if (obj == null) {
            return null;
        }
        Object val = obj;
        return (V)val;
    }

    public <T extends Flag<V>, V> void setFlag(T flag, V val) {
        if (val == null) {
            this.flags.remove(flag);
        } else {
            this.flags.put(flag, val);
        }
    }

    public Map<Flag<?>, Object> getFlags() {
        return this.flags;
    }

    public void setFlags(Map<Flag<?>, Object> flags) {
        this.flags = flags;
    }

    public abstract List<BlockVector2D> getPoints();

    public abstract int volume();

    public abstract boolean contains(Vector var1);

    public boolean contains(BlockVector2D pt) {
        return this.contains(new Vector(pt.getBlockX(), this.min.getBlockY(), pt.getBlockZ()));
    }

    public boolean contains(int x, int y, int z) {
        return this.contains(new Vector(x, y, z));
    }

    public boolean containsAny(List<BlockVector2D> pts) {
        for (BlockVector2D pt : pts) {
            if (!this.contains(pt)) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(ProtectedRegion other) {
        if (this.priority > other.priority) {
            return -1;
        }
        if (this.priority < other.priority) {
            return 1;
        }
        return this.id.compareTo(other.id);
    }

    public abstract String getTypeName();

    public abstract List<ProtectedRegion> getIntersectingRegions(List<ProtectedRegion> var1) throws UnsupportedIntersectionException;

    protected boolean intersectsBoundingBox(ProtectedRegion region) {
        BlockVector rMaxPoint = region.getMaximumPoint();
        BlockVector min = this.getMinimumPoint();
        if (rMaxPoint.getBlockX() < min.getBlockX()) {
            return false;
        }
        if (rMaxPoint.getBlockY() < min.getBlockY()) {
            return false;
        }
        if (rMaxPoint.getBlockZ() < min.getBlockZ()) {
            return false;
        }
        BlockVector rMinPoint = region.getMinimumPoint();
        BlockVector max = this.getMaximumPoint();
        if (rMinPoint.getBlockX() > max.getBlockX()) {
            return false;
        }
        if (rMinPoint.getBlockY() > max.getBlockY()) {
            return false;
        }
        return rMinPoint.getBlockZ() <= max.getBlockZ();
    }

    protected boolean intersectsEdges(ProtectedRegion region) {
        List<BlockVector2D> pts1 = this.getPoints();
        List<BlockVector2D> pts2 = region.getPoints();
        BlockVector2D lastPt1 = pts1.get(pts1.size() - 1);
        BlockVector2D lastPt2 = pts2.get(pts2.size() - 1);
        for (BlockVector2D aPts1 : pts1) {
            for (BlockVector2D aPts2 : pts2) {
                Line2D.Double line1 = new Line2D.Double(lastPt1.getBlockX(), lastPt1.getBlockZ(), aPts1.getBlockX(), aPts1.getBlockZ());
                if (line1.intersectsLine(lastPt2.getBlockX(), lastPt2.getBlockZ(), aPts2.getBlockX(), aPts2.getBlockZ())) {
                    return true;
                }
                lastPt2 = aPts2;
            }
            lastPt1 = aPts1;
        }
        return false;
    }

    public static boolean isValidId(String id) {
        return idPattern.matcher(id).matches();
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ProtectedRegion)) {
            return false;
        }
        ProtectedRegion other = (ProtectedRegion)obj;
        return other.getId().equals(this.getId());
    }

    public static class CircularInheritanceException
    extends Exception {
        private static final long serialVersionUID = 7479613488496776022L;
    }
}

