/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.query.SqlTreeNode;
import com.avaje.ebeaninternal.server.query.SqlTreeNodeBean;
import com.avaje.ebeaninternal.server.query.SqlTreeProperties;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class SqlTreeNodeRoot
extends SqlTreeNodeBean {
    private final TableJoin includeJoin;

    public SqlTreeNodeRoot(BeanDescriptor<?> desc, SqlTreeProperties props, List<SqlTreeNode> myList, boolean withId, TableJoin includeJoin) {
        super(null, null, desc, props, myList, withId);
        this.includeJoin = includeJoin;
    }

    public SqlTreeNodeRoot(BeanDescriptor<?> desc, SqlTreeProperties props, List<SqlTreeNode> myList, boolean withId) {
        super(null, null, desc, props, myList, withId);
        this.includeJoin = null;
    }

    @Override
    protected void postLoad(DbReadContext cquery, Object loadedBean, Object id) {
        cquery.setLoadedBean(loadedBean, id);
    }

    @Override
    public boolean appendFromBaseTable(DbSqlContext ctx, boolean forceOuterJoin) {
        ctx.append(this.desc.getBaseTable());
        ctx.append(" ").append(ctx.getTableAlias(null));
        if (this.includeJoin != null) {
            String a1 = ctx.getTableAlias(null);
            String a2 = "int_";
            this.includeJoin.addJoin(forceOuterJoin, a1, a2, ctx);
        }
        return forceOuterJoin;
    }
}

