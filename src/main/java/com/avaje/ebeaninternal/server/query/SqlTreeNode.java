/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import java.sql.SQLException;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SqlTreeNode {
    public static final char NEW_LINE = '\n';
    public static final String PERIOD = ".";
    public static final String COMMA = ", ";
    public static final int NORMAL = 0;
    public static final int SHARED = 1;
    public static final int READONLY = 2;

    public void buildSelectExpressionChain(List<String> var1);

    public void appendSelect(DbSqlContext var1, boolean var2);

    public void appendFrom(DbSqlContext var1, boolean var2);

    public void appendWhere(DbSqlContext var1);

    public void load(DbReadContext var1, Object var2, int var3) throws SQLException;
}

