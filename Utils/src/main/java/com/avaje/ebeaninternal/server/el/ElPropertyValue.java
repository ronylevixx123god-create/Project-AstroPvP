/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.el;

import com.avaje.ebean.text.StringFormatter;
import com.avaje.ebean.text.StringParser;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;

public interface ElPropertyValue
extends ElPropertyDeploy {
    public Object[] getAssocOneIdValues(Object var1);

    public String getAssocOneIdExpr(String var1, String var2);

    public String getAssocIdInValueExpr(int var1);

    public String getAssocIdInExpr(String var1);

    public boolean isAssocId();

    public boolean isAssocProperty();

    public boolean isLocalEncrypted();

    public boolean isDbEncrypted();

    public int getDeployOrder();

    public StringParser getStringParser();

    public StringFormatter getStringFormatter();

    public boolean isDateTimeCapable();

    public int getJdbcType();

    public Object parseDateTime(long var1);

    public Object elGetValue(Object var1);

    public Object elGetReference(Object var1);

    public void elSetValue(Object var1, Object var2, boolean var3, boolean var4);

    public void elSetReference(Object var1);

    public Object elConvertType(Object var1);
}

