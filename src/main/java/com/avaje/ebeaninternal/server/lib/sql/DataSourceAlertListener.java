/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.sql;

public interface DataSourceAlertListener {
    public void dataSourceDown(String var1);

    public void dataSourceUp(String var1);

    public void warning(String var1, String var2);
}

