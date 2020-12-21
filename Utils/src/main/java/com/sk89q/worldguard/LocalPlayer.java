/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard;

import com.sk89q.worldedit.Vector;

public abstract class LocalPlayer {
    public abstract String getName();

    public abstract boolean hasGroup(String var1);

    public abstract Vector getPosition();

    public abstract void kick(String var1);

    public abstract void ban(String var1);

    public abstract void printRaw(String var1);

    public abstract String[] getGroups();

    public abstract boolean hasPermission(String var1);

    public boolean equals(Object obj) {
        return obj instanceof LocalPlayer && ((LocalPlayer)obj).getName().equals(this.getName());
    }

    public int hashCode() {
        return this.getName().hashCode();
    }
}

