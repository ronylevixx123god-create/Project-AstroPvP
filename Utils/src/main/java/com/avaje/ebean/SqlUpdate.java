/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

public interface SqlUpdate {
    public int execute();

    public boolean isAutoTableMod();

    public SqlUpdate setAutoTableMod(boolean var1);

    public String getLabel();

    public SqlUpdate setLabel(String var1);

    public String getSql();

    public int getTimeout();

    public SqlUpdate setTimeout(int var1);

    public SqlUpdate setParameter(int var1, Object var2);

    public SqlUpdate setNull(int var1, int var2);

    public SqlUpdate setNullParameter(int var1, int var2);

    public SqlUpdate setParameter(String var1, Object var2);

    public SqlUpdate setNull(String var1, int var2);

    public SqlUpdate setNullParameter(String var1, int var2);
}

