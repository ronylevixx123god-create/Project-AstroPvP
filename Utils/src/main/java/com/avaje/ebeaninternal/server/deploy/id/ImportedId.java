/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import java.sql.SQLException;

public interface ImportedId {
    public void addFkeys(String var1);

    public boolean isScalar();

    public String getLogicalName();

    public String getDbColumn();

    public void sqlAppend(DbSqlContext var1);

    public void dmlAppend(GenerateDmlRequest var1);

    public void dmlWhere(GenerateDmlRequest var1, Object var2);

    public boolean hasChanged(Object var1, Object var2);

    public void bind(BindableRequest var1, Object var2, boolean var3) throws SQLException;

    public void buildImport(IntersectionRow var1, Object var2);

    public BeanProperty findMatchImport(String var1);
}

