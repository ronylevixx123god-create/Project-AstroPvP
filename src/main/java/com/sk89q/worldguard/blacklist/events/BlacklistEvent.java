/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.blacklist.events;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;

public abstract class BlacklistEvent {
    private Vector pos;
    private int type;
    private LocalPlayer player;

    public BlacklistEvent(LocalPlayer player, Vector pos, int type) {
        this.player = player;
        this.pos = pos;
        this.type = type;
    }

    public LocalPlayer getPlayer() {
        return this.player;
    }

    public Vector getPosition() {
        return this.pos;
    }

    public int getType() {
        return this.type;
    }

    public abstract String getDescription();
}

