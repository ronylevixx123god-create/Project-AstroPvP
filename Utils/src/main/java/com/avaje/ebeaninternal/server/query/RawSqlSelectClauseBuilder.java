/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.Query;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
import com.avaje.ebean.config.dbplatform.SqlLimiter;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.DRawSqlSelect;
import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
import com.avaje.ebeaninternal.server.deploy.DeployParser;
import com.avaje.ebeaninternal.server.persist.Binder;
import com.avaje.ebeaninternal.server.query.CQuery;
import com.avaje.ebeaninternal.server.query.CQueryPlan;
import com.avaje.ebeaninternal.server.query.CQueryPredicates;
import com.avaje.ebeaninternal.server.query.SqlTree;
import com.avaje.ebeaninternal.server.query.SqlTreeAlias;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class RawSqlSelectClauseBuilder {
    private static final Logger logger = Logger.getLogger(RawSqlSelectClauseBuilder.class.getName());
    private final Binder binder;
    private final SqlLimiter dbQueryLimiter;

    public RawSqlSelectClauseBuilder(DatabasePlatform dbPlatform, Binder binder) {
        this.binder = binder;
        this.dbQueryLimiter = dbPlatform.getSqlLimiter();
    }

    public <T> CQuery<T> build(OrmQueryRequest<T> request) throws PersistenceException {
        Query query = request.getQuery();
        BeanDescriptor<T> desc = request.getBeanDescriptor();
        DeployNamedQuery namedQuery = desc.getNamedQuery(query.getName());
        DRawSqlSelect sqlSelect = namedQuery.getSqlSelect();
        DeployParser parser = sqlSelect.createDeployPropertyParser();
        CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
        predicates.prepareRawSql(parser);
        SqlTreeAlias alias = new SqlTreeAlias(sqlSelect.getTableAlias());
        predicates.parseTableAlias(alias);
        String sql = null;
        try {
            boolean includeRowNumColumn = false;
            String orderBy = sqlSelect.getOrderBy(predicates);
            sql = sqlSelect.buildSql(orderBy, predicates, request);
            if (query.hasMaxRowsOrFirstRow() && this.dbQueryLimiter != null) {
                SqlLimitResponse limitSql = this.dbQueryLimiter.limit(new OrmQueryLimitRequest(sql, orderBy, (SpiQuery<?>)query));
                includeRowNumColumn = limitSql.isIncludesRowNumberColumn();
                sql = limitSql.getSql();
            } else {
                sql = "select " + sql;
            }
            SqlTree sqlTree = sqlSelect.getSqlTree();
            CQueryPlan queryPlan = new CQueryPlan(sql, sqlTree, true, includeRowNumColumn, "");
            CQuery<T> compiledQuery = new CQuery<T>(request, predicates, queryPlan);
            return compiledQuery;
        }
        catch (Exception e) {
            String msg = "Error with " + desc.getFullName() + " query:\r" + sql;
            logger.log(Level.SEVERE, msg);
            throw new PersistenceException(e);
        }
    }
}

