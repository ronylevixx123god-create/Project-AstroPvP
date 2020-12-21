/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import java.util.concurrent.TimeUnit;

public interface BackgroundExecutor {
    public void execute(Runnable var1);

    public void executePeriodically(Runnable var1, long var2, TimeUnit var4);
}

