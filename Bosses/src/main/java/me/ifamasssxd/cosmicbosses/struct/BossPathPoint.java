/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.ReflectionUtils
 *  net.minecraft.server.v1_7_R4.PathPoint
 */
package me.ifamasssxd.cosmicbosses.struct;

import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.PathPoint;

public class BossPathPoint
extends PathPoint {
    private final Field d;
    private final Field e;
    private final Field f;
    private final Field g;
    private final Field h;
    private BossPathPoint previous;

    public BossPathPoint(int i, int i1, int i2) {
        super(i, i1, i2);
        Class<?> superClass = ((Object)((Object)this)).getClass().getSuperclass();
        this.d = ReflectionUtils.getField(superClass, (String)"d");
        this.e = ReflectionUtils.getField(superClass, (String)"e");
        this.f = ReflectionUtils.getField(superClass, (String)"f");
        this.g = ReflectionUtils.getField(superClass, (String)"g");
        this.h = ReflectionUtils.getField(superClass, (String)"h");
    }

    public int getX() {
        return this.a;
    }

    public int getY() {
        return this.b;
    }

    public int getZ() {
        return this.c;
    }

    public int getIndex() {
        try {
            return (Integer)this.d.get((Object)this);
        }
        catch (IllegalAccessException e1) {
            e1.printStackTrace();
            return -1;
        }
    }

    public void setIndex(int index) {
        try {
            this.d.set((Object)this, index);
        }
        catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }

    public float getTotalPathPointDistance() {
        try {
            return ((Float)this.e.get((Object)this)).floatValue();
        }
        catch (IllegalAccessException e1) {
            e1.printStackTrace();
            return 0.0f;
        }
    }

    public void setTotalPathDistance(float value) {
        try {
            this.e.set((Object)this, Float.valueOf(value));
        }
        catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }

    public float getDistanceToNext() {
        try {
            return ((Float)this.f.get((Object)this)).floatValue();
        }
        catch (IllegalAccessException e1) {
            e1.printStackTrace();
            return 0.0f;
        }
    }

    public void setDistanceToNext(float distance) {
        try {
            this.f.set((Object)this, Float.valueOf(distance));
        }
        catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }

    public float getDistanceToTarget() {
        try {
            return ((Float)this.g.get((Object)this)).floatValue();
        }
        catch (IllegalAccessException e1) {
            e1.printStackTrace();
            return 0.0f;
        }
    }

    public void setDistanceToTarget(float distance) {
        try {
            this.g.set((Object)this, Float.valueOf(distance));
        }
        catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }

    public BossPathPoint getPrevious() {
        return this.previous;
    }

    public void setPrevious(BossPathPoint point) {
        this.previous = point;
    }

    public boolean isVisited() {
        return this.i;
    }

    public void setVisited(boolean val) {
        this.i = val;
    }

    public boolean isAssigned() {
        return this.a();
    }

    public float distanceTo(PathPoint point) {
        return this.a(point);
    }

    public float distanceToSquared(PathPoint point) {
        return this.b(point);
    }
}

