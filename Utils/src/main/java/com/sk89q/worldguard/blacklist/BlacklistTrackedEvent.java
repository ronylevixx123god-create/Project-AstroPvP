/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.blacklist;

import com.sk89q.worldguard.blacklist.events.BlacklistEvent;

public class BlacklistTrackedEvent {
    private BlacklistEvent event;
    private long time;

    public BlacklistTrackedEvent(BlacklistEvent event, long time) {
        this.event = event;
        this.time = time;
    }

    public boolean matches(BlacklistEvent other, long now) {
        return other.getType() == this.event.getType() && this.time > now - 3000L && other.getClass() == this.event.getClass();
    }
}

