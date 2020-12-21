/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;

public interface EventExecutor {
    public void execute(Listener var1, Event var2) throws EventException;
}

