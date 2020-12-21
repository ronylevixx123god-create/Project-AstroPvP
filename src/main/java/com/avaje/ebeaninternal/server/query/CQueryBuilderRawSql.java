/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
import com.avaje.ebean.config.dbplatform.SqlLimiter;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.query.CQueryPredicates;
import com.avaje.ebeaninternal.server.query.Constants;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
import com.avaje.ebeaninternal.server.util.BindParamsParser;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CQueryBuilderRawSql
implements Constants {
    private final SqlLimiter sqlLimiter;

    CQueryBuilderRawSql(SqlLimiter sqlLimiter) {
        this.sqlLimiter = sqlLimiter;
    }

    public SqlLimitResponse buildSql(OrmQueryRequest<?> request, CQueryPredicates predicates, RawSql.Sql rsql) {
        if (!rsql.isParsed()) {
            String sql = rsql.getUnparsedSql();
            BindParams bindParams = request.getQuery().getBindParams();
            if (bindParams != null && bindParams.requiresNamedParamsPrepare()) {
                sql = BindParamsParser.parse(bindParams, sql);
            }
            return new SqlLimitResponse(sql, false);
        }
        String orderBy = this.getOrderBy(predicates, rsql);
        String sql = this.buildMainQuery(orderBy, request, predicates, rsql);
        Query query = request.getQuery();
        if (query.hasMaxRowsOrFirstRow() && this.sqlLimiter != null) {
            return this.sqlLimiter.limit(new OrmQueryLimitRequest(sql, orderBy, (SpiQuery<?>)query));
        }
        sql = "select " + sql;
        return new SqlLimitResponse(sql, false);
    }

    private String buildMainQuery(String orderBy, OrmQueryRequest<?> request, CQueryPredicates predicates, RawSql.Sql sql) {
        String dbHaving;
        String preHaving;
        String dbWhere;
        StringBuilder sb = new StringBuilder();
        sb.append(sql.getPreFrom());
        sb.append(" ");
        sb.append('\n');
        String s = sql.getPreWhere();
        BindParams bindParams = request.getQuery().getBindParams();
        if (bindParams != null && bindParams.requiresNamedParamsPrepare()) {
            s = BindParamsParser.parse(bindParams, s);
        }
        sb.append(s);
        sb.append(" ");
        String dynamicWhere = null;
        if (request.getQuery().getId() != null) {
            BeanDescriptor<?> descriptor = request.getBeanDescriptor();
            dynamicWhere = descriptor.getIdBinderIdSql();
        }
        if (!this.isEmpty(dbWhere = predicates.getDbWhere())) {
            dynamicWhere = dynamicWhere == null ? dbWhere : dynamicWhere + " and " + dbWhere;
        }
        if (!this.isEmpty(dynamicWhere)) {
            sb.append('\n');
            if (sql.isAndWhereExpr()) {
                sb.append("and ");
            } else {
                sb.append("where ");
            }
            sb.append(dynamicWhere);
            sb.append(" ");
        }
        if (!this.isEmpty(preHaving = sql.getPreHaving())) {
            sb.append('\n');
            sb.append(preHaving);
            sb.append(" ");
        }
        if (!this.isEmpty(dbHaving = predicates.getDbHaving())) {
            sb.append(" ");
            sb.append('\n');
            if (sql.isAndHavingExpr()) {
                sb.append("and ");
            } else {
                sb.append("having ");
            }
            sb.append(dbHaving);
            sb.append(" ");
        }
        if (!this.isEmpty(orderBy)) {
            sb.append('\n');
            sb.append(" order by ").append(orderBy);
        }
        return sb.toString().trim();
    }

    private boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    private String getOrderBy(CQueryPredicates predicates, RawSql.Sql sql) {
        String orderBy = predicates.getDbOrderBy();
        if (orderBy != null) {
            return orderBy;
        }
        return sql.getOrderBy();
    }
}

