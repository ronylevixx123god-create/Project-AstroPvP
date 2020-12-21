/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.annotation.SqlSelect;

public class DRawSqlMeta {
    private String name;
    private String tableAlias;
    private String extend;
    private String query;
    private boolean debug;
    private String where;
    private String having;
    private String columnMapping;

    public DRawSqlMeta(SqlSelect sqlSelect) {
        this.debug = sqlSelect.debug();
        this.name = sqlSelect.name();
        this.tableAlias = DRawSqlMeta.toNull(sqlSelect.tableAlias());
        this.extend = DRawSqlMeta.toNull(sqlSelect.extend());
        this.having = DRawSqlMeta.toNull(sqlSelect.having());
        this.where = DRawSqlMeta.toNull(sqlSelect.where());
        this.columnMapping = DRawSqlMeta.toNull(sqlSelect.columnMapping());
        this.query = DRawSqlMeta.toNull(sqlSelect.query());
    }

    public DRawSqlMeta(String name, String extend, String query, boolean debug, String where, String having, String columnMapping) {
        this.name = name;
        this.extend = extend;
        this.query = query;
        this.debug = debug;
        this.having = having;
        this.where = where;
        this.columnMapping = columnMapping;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getTableAlias() {
        return this.tableAlias;
    }

    public String getExtend() {
        return this.extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getWhere() {
        return this.where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getHaving() {
        return this.having;
    }

    public void setHaving(String having) {
        this.having = having;
    }

    public String getColumnMapping() {
        return this.columnMapping;
    }

    public void setColumnMapping(String columnMapping) {
        this.columnMapping = columnMapping;
    }

    public void extend(DRawSqlMeta parentQuery) {
        this.extendQuery(parentQuery.getQuery());
        this.extendColumnMapping(parentQuery.getColumnMapping());
    }

    private void extendQuery(String parentSql) {
        this.query = this.query == null ? parentSql : parentSql + " " + this.query;
    }

    private void extendColumnMapping(String parentColumnMapping) {
        if (this.columnMapping == null) {
            this.columnMapping = parentColumnMapping;
        }
    }

    private static String toNull(String s) {
        if (s != null && s.equals("")) {
            return null;
        }
        return s;
    }
}

