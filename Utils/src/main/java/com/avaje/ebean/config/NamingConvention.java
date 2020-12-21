/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config;

import com.avaje.ebean.config.TableName;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface NamingConvention {
    public void setDatabasePlatform(DatabasePlatform var1);

    public TableName getTableName(Class<?> var1);

    public TableName getM2MJoinTableName(TableName var1, TableName var2);

    public String getColumnFromProperty(Class<?> var1, String var2);

    public String getPropertyFromColumn(Class<?> var1, String var2);

    public String getSequenceName(String var1, String var2);

    public boolean isUseForeignKeyPrefix();
}

