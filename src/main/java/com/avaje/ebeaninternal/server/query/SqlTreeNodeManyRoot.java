/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.query.SqlTreeNode;
import com.avaje.ebeaninternal.server.query.SqlTreeNodeBean;
import com.avaje.ebeaninternal.server.query.SqlTreeProperties;
import java.sql.SQLException;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class SqlTreeNodeManyRoot
extends SqlTreeNodeBean {
    public SqlTreeNodeManyRoot(String prefix, BeanPropertyAssocMany<?> prop, SqlTreeProperties props, List<SqlTreeNode> myList) {
        super(prefix, prop, prop.getTargetDescriptor(), props, myList, true);
    }

    @Override
    protected void postLoad(DbReadContext cquery, Object loadedBean, Object id) {
        cquery.setLoadedManyBean(loadedBean);
    }

    @Override
    public void load(DbReadContext cquery, Object parentBean, int parentState) throws SQLException {
        super.load(cquery, null, parentState);
    }

    @Override
    public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
        super.appendFrom(ctx, true);
    }
}

