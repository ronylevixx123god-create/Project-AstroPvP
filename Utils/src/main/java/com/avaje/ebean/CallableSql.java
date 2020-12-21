/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import java.sql.CallableStatement;
import java.sql.SQLException;

public interface CallableSql {
    public String getLabel();

    public CallableSql setLabel(String var1);

    public int getTimeout();

    public String getSql();

    public CallableSql setTimeout(int var1);

    public CallableSql setSql(String var1);

    public CallableSql bind(int var1, Object var2);

    public CallableSql setParameter(int var1, Object var2);

    public CallableSql registerOut(int var1, int var2);

    public Object getObject(int var1);

    public boolean executeOverride(CallableStatement var1) throws SQLException;

    public CallableSql addModification(String var1, boolean var2, boolean var3, boolean var4);
}

