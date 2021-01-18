package me.ifamasssxd.cosmicbosses.struct;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Vec3D;

import javax.annotation.Nullable;

public class BossPath {
    private final BossPathPoint[] points;
    private BossPathPoint[] openSet = new BossPathPoint[0];
    private BossPathPoint[] closedSet = new BossPathPoint[0];
    private BossPathPoint target;
    private int currentPathIndex;
    private int pathLength;

    public BossPath(BossPathPoint[] pathpoints) {
        this.points = pathpoints;
        this.pathLength = pathpoints.length;
    }

    public void incrementPathIndex() {
        ++this.currentPathIndex;
    }

    public boolean isFinished() {
        return this.currentPathIndex >= this.pathLength;
    }

    @Nullable
    public BossPathPoint getFinalPathPoint() {
        return this.pathLength > 0 ? this.points[this.pathLength - 1] : null;
    }

    public BossPathPoint getPathPointFromIndex(int index) {
        return this.points[index];
    }

    public void setPoint(int index, BossPathPoint point) {
        this.points[index] = point;
    }

    public int getCurrentPathLength() {
        return this.pathLength;
    }

    public void setCurrentPathLength(int length) {
        this.pathLength = length;
    }

    public int getCurrentPathIndex() {
        return this.currentPathIndex;
    }

    public void setCurrentPathIndex(int currentPathIndexIn) {
        this.currentPathIndex = currentPathIndexIn;
    }

    public Vec3D getVectorFromIndex(Entity entityIn, int index) {
        double d0 = (double)this.points[index].getX() + (double)((int)(entityIn.width + 1.0f)) * 0.5;
        double d1 = this.points[index].getY();
        double d2 = (double)this.points[index].getZ() + (double)((int)(entityIn.width + 1.0f)) * 0.5;
        return Vec3D.a((double)d0, (double)d1, (double)d2);
    }

    public Vec3D getPosition(Entity entityIn) {
        return this.getVectorFromIndex(entityIn, this.currentPathIndex);
    }

    public Vec3D getCurrentPos() {
        BossPathPoint pathpoint = this.points[this.currentPathIndex];
        return Vec3D.a((double)pathpoint.getX(), (double)pathpoint.getY(), (double)pathpoint.getZ());
    }

    public boolean isSamePath(BossPath pathentityIn) {
        if (pathentityIn == null) {
            return false;
        }
        if (pathentityIn.points.length != this.points.length) {
            return false;
        }
        for (int i = 0; i < this.points.length; ++i) {
            if (this.points[i].getX() == pathentityIn.points[i].getX() && this.points[i].getY() == pathentityIn.points[i].getY() && this.points[i].getZ() == pathentityIn.points[i].getZ()) continue;
            return false;
        }
        return true;
    }

    public BossPathPoint[] getOpenSet() {
        return this.openSet;
    }

    public BossPathPoint[] getClosedSet() {
        return this.closedSet;
    }

    public BossPathPoint getTarget() {
        return this.target;
    }
}

