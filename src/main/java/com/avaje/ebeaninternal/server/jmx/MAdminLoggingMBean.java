/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.jmx;

import com.avaje.ebean.LogLevel;

public interface MAdminLoggingMBean {
    public LogLevel getLogLevel();

    public void setLogLevel(LogLevel var1);

    public boolean isDebugGeneratedSql();

    public void setDebugGeneratedSql(boolean var1);

    public boolean isDebugLazyLoad();

    public void setDebugLazyLoad(boolean var1);
}

