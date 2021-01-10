/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package com.compilex.CompilexPermissions.event;

import com.compilex.CompilexPermissions.structure.PermissionUser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PermissionChangeEvent
extends Event {
    private static final HandlerList handlers = new HandlerList();
    private PermissionUser user;
    private boolean cancelled;

    public PermissionChangeEvent(PermissionUser user) {
        this.user = user;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public PermissionUser getUser() {
        return this.user;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

