/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.querydefn;

import com.avaje.ebean.config.dbplatform.SqlLimitRequest;
import com.avaje.ebeaninternal.api.SpiQuery;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class OrmQueryLimitRequest
implements SqlLimitRequest {
    final SpiQuery<?> ormQuery;
    final String sql;
    final String sqlOrderBy;

    public OrmQueryLimitRequest(String sql, String sqlOrderBy, SpiQuery<?> ormQuery) {
        this.sql = sql;
        this.sqlOrderBy = sqlOrderBy;
        this.ormQuery = ormQuery;
    }

    @Override
    public String getDbOrderBy() {
        return this.sqlOrderBy;
    }

    @Override
    public String getDbSql() {
        return this.sql;
    }

    @Override
    public int getFirstRow() {
        return this.ormQuery.getFirstRow();
    }

    @Override
    public int getMaxRows() {
        return this.ormQuery.getMaxRows();
    }

    @Override
    public boolean isDistinct() {
        return this.ormQuery.isDistinct();
    }
}

