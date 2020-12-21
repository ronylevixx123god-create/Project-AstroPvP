/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.BeanCascadeInfo;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class TableJoin {
    public static final String NEW_LINE = "\n";
    public static final String LEFT_OUTER = "left outer join";
    public static final String JOIN = "join";
    private final boolean importedPrimaryKey;
    private final String table;
    private final String type;
    private final BeanCascadeInfo cascadeInfo;
    private final BeanProperty[] properties;
    private final TableJoinColumn[] columns;

    public TableJoin(DeployTableJoin deploy, LinkedHashMap<String, BeanProperty> propMap) {
        this.importedPrimaryKey = deploy.isImportedPrimaryKey();
        this.table = InternString.intern(deploy.getTable());
        this.type = InternString.intern(deploy.getType());
        this.cascadeInfo = deploy.getCascadeInfo();
        DeployTableJoinColumn[] deployCols = deploy.columns();
        this.columns = new TableJoinColumn[deployCols.length];
        for (int i = 0; i < deployCols.length; ++i) {
            this.columns[i] = new TableJoinColumn(deployCols[i]);
        }
        DeployBeanProperty[] deployProps = deploy.properties();
        if (deployProps.length > 0 && propMap == null) {
            throw new NullPointerException("propMap is null?");
        }
        this.properties = new BeanProperty[deployProps.length];
        for (int i = 0; i < deployProps.length; ++i) {
            BeanProperty prop;
            this.properties[i] = prop = propMap.get(deployProps[i].getName());
        }
    }

    public TableJoin createWithAlias(String localAlias, String foreignAlias) {
        return new TableJoin(this, localAlias, foreignAlias);
    }

    private TableJoin(TableJoin join, String localAlias, String foreignAlias) {
        this.importedPrimaryKey = join.importedPrimaryKey;
        this.table = join.table;
        this.type = join.type;
        this.cascadeInfo = join.cascadeInfo;
        this.properties = join.properties;
        this.columns = join.columns;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(30);
        sb.append(this.type).append(" ").append(this.table).append(" ");
        for (int i = 0; i < this.columns.length; ++i) {
            sb.append(this.columns[i]).append(" ");
        }
        return sb.toString();
    }

    public void appendSelect(DbSqlContext ctx, boolean subQuery) {
        int x = this.properties.length;
        for (int i = 0; i < x; ++i) {
            this.properties[i].appendSelect(ctx, subQuery);
        }
    }

    public void load(SqlBeanLoad sqlBeanLoad) throws SQLException {
        int x = this.properties.length;
        for (int i = 0; i < x; ++i) {
            this.properties[i].load(sqlBeanLoad);
        }
    }

    public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
        int x = this.properties.length;
        for (int i = 0; i < x; ++i) {
            this.properties[i].readSet(ctx, bean, type);
        }
        return null;
    }

    public boolean isImportedPrimaryKey() {
        return this.importedPrimaryKey;
    }

    public BeanCascadeInfo getCascadeInfo() {
        return this.cascadeInfo;
    }

    public TableJoinColumn[] columns() {
        return this.columns;
    }

    public BeanProperty[] properties() {
        return this.properties;
    }

    public String getTable() {
        return this.table;
    }

    public String getType() {
        return this.type;
    }

    public boolean isOuterJoin() {
        return this.type.equals(LEFT_OUTER);
    }

    public boolean addJoin(boolean forceOuterJoin, String prefix, DbSqlContext ctx) {
        String[] names = SplitName.split(prefix);
        String a1 = ctx.getTableAlias(names[0]);
        String a2 = ctx.getTableAlias(prefix);
        return this.addJoin(forceOuterJoin, a1, a2, ctx);
    }

    public boolean addJoin(boolean forceOuterJoin, String a1, String a2, DbSqlContext ctx) {
        ctx.addJoin(forceOuterJoin ? LEFT_OUTER : this.type, this.table, this.columns(), a1, a2);
        return forceOuterJoin || LEFT_OUTER.equals(this.type);
    }

    public void addInnerJoin(String a1, String a2, DbSqlContext ctx) {
        ctx.addJoin(JOIN, this.table, this.columns(), a1, a2);
    }
}

