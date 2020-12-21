/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.blacklist.events;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.blacklist.events.ItemBlacklistEvent;

public class ItemAcquireBlacklistEvent
extends ItemBlacklistEvent {
    public ItemAcquireBlacklistEvent(LocalPlayer player, Vector pos, int type) {
        super(player, pos, type);
    }

    @Override
    public String getDescription() {
        return "acquire";
    }
}

