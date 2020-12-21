/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.blacklist.loggers;

import com.sk89q.worldguard.blacklist.events.BlacklistEvent;

public interface BlacklistLoggerHandler {
    public void logEvent(BlacklistEvent var1, String var2);

    public void close();
}

