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

public class PermissionGroupAddEvent
extends Event {
    private static final HandlerList handlers = new HandlerList();
    private PermissionUser user;
    private String newGroup;
    private boolean cancelled;

    public PermissionGroupAddEvent(PermissionUser user, String newGroup) {
        this.user = user;
        this.newGroup = newGroup;
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

    public String getNewGroup() {
        return this.newGroup;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

