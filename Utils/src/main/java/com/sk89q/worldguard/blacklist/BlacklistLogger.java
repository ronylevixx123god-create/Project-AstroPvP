/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.blacklist;

import com.sk89q.worldguard.blacklist.events.BlacklistEvent;
import com.sk89q.worldguard.blacklist.loggers.BlacklistLoggerHandler;
import java.util.HashSet;
import java.util.Set;

public class BlacklistLogger
implements BlacklistLoggerHandler {
    private Set<BlacklistLoggerHandler> handlers = new HashSet<BlacklistLoggerHandler>();

    public void addHandler(BlacklistLoggerHandler handler) {
        this.handlers.add(handler);
    }

    public void removeHandler(BlacklistLoggerHandler handler) {
        this.handlers.remove(handler);
    }

    public void clearHandlers() {
        this.handlers.clear();
    }

    @Override
    public void logEvent(BlacklistEvent event, String comment) {
        for (BlacklistLoggerHandler handler : this.handlers) {
            handler.logEvent(event, comment);
        }
    }

    @Override
    public void close() {
        for (BlacklistLoggerHandler handler : this.handlers) {
            handler.close();
        }
    }
}

