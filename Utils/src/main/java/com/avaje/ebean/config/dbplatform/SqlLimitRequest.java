/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config.dbplatform;

public interface SqlLimitRequest {
    public boolean isDistinct();

    public int getFirstRow();

    public int getMaxRows();

    public String getDbSql();

    public String getDbOrderBy();
}

