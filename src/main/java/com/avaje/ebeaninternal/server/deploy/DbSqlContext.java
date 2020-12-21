/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;

public interface DbSqlContext {
    public void addJoin(String var1, String var2, TableJoinColumn[] var3, String var4, String var5);

    public void pushSecondaryTableAlias(String var1);

    public void pushTableAlias(String var1);

    public void popTableAlias();

    public void addEncryptedProp(BeanProperty var1);

    public BeanProperty[] getEncryptedProps();

    public DbSqlContext append(char var1);

    public DbSqlContext append(String var1);

    public String peekTableAlias();

    public void appendRawColumn(String var1);

    public void appendColumn(String var1, String var2);

    public void appendColumn(String var1);

    public void appendFormulaSelect(String var1);

    public void appendFormulaJoin(String var1, boolean var2);

    public int length();

    public String getContent();

    public String peekJoin();

    public void pushJoin(String var1);

    public void popJoin();

    public String getTableAlias(String var1);

    public String getTableAliasManyWhere(String var1);

    public String getRelativePrefix(String var1);
}

