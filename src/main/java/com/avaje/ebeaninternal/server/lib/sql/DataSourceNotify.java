/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.sql;

public interface DataSourceNotify {
    public void notifyDataSourceUp(String var1);

    public void notifyDataSourceDown(String var1);

    public void notifyWarning(String var1, String var2);
}

