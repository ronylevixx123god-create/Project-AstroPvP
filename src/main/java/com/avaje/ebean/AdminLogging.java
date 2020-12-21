/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.LogLevel;

public interface AdminLogging {
    public void setLogLevel(LogLevel var1);

    public LogLevel getLogLevel();

    public boolean isDebugGeneratedSql();

    public void setDebugGeneratedSql(boolean var1);

    public boolean isDebugLazyLoad();

    public void setDebugLazyLoad(boolean var1);
}

