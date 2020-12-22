/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.struct;

import me.ifamasssxd.cosmicbosses.struct.BossPathPoint;

public class BossPathHeap {
    private BossPathPoint[] pathPoints = new BossPathPoint[128];
    private int count;

    public BossPathPoint addPoint(BossPathPoint point) {
        if (point.getIndex() >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }
        if (this.count == this.pathPoints.length) {
            BossPathPoint[] apathpoint = new BossPathPoint[this.count << 1];
            System.arraycopy(this.pathPoints, 0, apathpoint, 0, this.count);
            this.pathPoints = apathpoint;
        }
        this.pathPoints[this.count] = point;
        point.setIndex(this.count);
        this.sortBack(this.count++);
        return point;
    }

    public void clearPath() {
        this.count = 0;
    }

    public BossPathPoint dequeue() {
        BossPathPoint pathpoint = this.pathPoints[0];
        this.pathPoints[0] = this.pathPoints[--this.count];
        this.pathPoints[this.count] = null;
        if (this.count > 0) {
            this.sortForward(0);
        }
        pathpoint.setIndex(-1);
        return pathpoint;
    }

    public void changeDistance(BossPathPoint point, float distance) {
        float f = point.getDistanceToTarget();
        point.setDistanceToTarget(distance);
        if (distance < f) {
            this.sortBack(point.getIndex());
        } else {
            this.sortForward(point.getIndex());
        }
    }

    private void sortBack(int index) {
        int i;
        BossPathPoint pathpoint1;
        BossPathPoint pathpoint = this.pathPoints[index];
        float f = pathpoint.getDistanceToTarget();
        while (index > 0 && !(f >= (pathpoint1 = this.pathPoints[i = index - 1 >> 1]).getDistanceToTarget())) {
            this.pathPoints[index] = pathpoint1;
            pathpoint1.setIndex(index);
            index = i;
        }
        this.pathPoints[index] = pathpoint;
        pathpoint.setIndex(index);
    }

    private void sortForward(int index) {
        BossPathPoint pathpoint = this.pathPoints[index];
        float f = pathpoint.getDistanceToTarget();
        while (true) {
            float f2;
            BossPathPoint pathpoint2;
            int i = 1 + (index << 1);
            int j = i + 1;
            if (i >= this.count) break;
            BossPathPoint pathpoint1 = this.pathPoints[i];
            float f1 = pathpoint1.getDistanceToTarget();
            if (j >= this.count) {
                pathpoint2 = null;
                f2 = Float.POSITIVE_INFINITY;
            } else {
                pathpoint2 = this.pathPoints[j];
                f2 = pathpoint2.getDistanceToTarget();
            }
            if (f1 < f2) {
                if (f1 >= f) break;
                this.pathPoints[index] = pathpoint1;
                pathpoint1.setIndex(index);
                index = i;
                continue;
            }
            if (f2 >= f) break;
            this.pathPoints[index] = pathpoint2;
            pathpoint2.setIndex(index);
            index = j;
        }
        this.pathPoints[index] = pathpoint;
        pathpoint.setIndex(index);
    }

    public boolean isPathEmpty() {
        return this.count == 0;
    }
}

