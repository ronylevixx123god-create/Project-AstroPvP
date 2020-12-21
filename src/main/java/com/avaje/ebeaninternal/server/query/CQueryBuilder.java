/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
import com.avaje.ebean.config.dbplatform.SqlLimiter;
import com.avaje.ebean.text.PathProperties;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.persist.Binder;
import com.avaje.ebeaninternal.server.query.CQuery;
import com.avaje.ebeaninternal.server.query.CQueryBuilderRawSql;
import com.avaje.ebeaninternal.server.query.CQueryFetchIds;
import com.avaje.ebeaninternal.server.query.CQueryPlan;
import com.avaje.ebeaninternal.server.query.CQueryPlanLucene;
import com.avaje.ebeaninternal.server.query.CQueryPlanRawSql;
import com.avaje.ebeaninternal.server.query.CQueryPredicates;
import com.avaje.ebeaninternal.server.query.CQueryRowCount;
import com.avaje.ebeaninternal.server.query.Constants;
import com.avaje.ebeaninternal.server.query.RawSqlSelectClauseBuilder;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.query.SqlTree;
import com.avaje.ebeaninternal.server.query.SqlTreeBuilder;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CQueryBuilder
implements Constants {
    private final String tableAliasPlaceHolder;
    private final String columnAliasPrefix;
    private final SqlLimiter sqlLimiter;
    private final RawSqlSelectClauseBuilder sqlSelectBuilder;
    private final CQueryBuilderRawSql rawSqlHandler;
    private final Binder binder;
    private final BackgroundExecutor backgroundExecutor;
    private final boolean selectCountWithAlias;
    private final boolean luceneAvailable;
    private final Query.UseIndex defaultUseIndex;

    public CQueryBuilder(BackgroundExecutor backgroundExecutor, DatabasePlatform dbPlatform, Binder binder, LuceneIndexManager luceneIndexManager) {
        this.luceneAvailable = luceneIndexManager.isLuceneAvailable();
        this.defaultUseIndex = luceneIndexManager.getDefaultUseIndex();
        this.backgroundExecutor = backgroundExecutor;
        this.binder = binder;
        this.tableAliasPlaceHolder = GlobalProperties.get("ebean.tableAliasPlaceHolder", "${ta}");
        this.columnAliasPrefix = GlobalProperties.get("ebean.columnAliasPrefix", "c");
        this.sqlSelectBuilder = new RawSqlSelectClauseBuilder(dbPlatform, binder);
        this.sqlLimiter = dbPlatform.getSqlLimiter();
        this.rawSqlHandler = new CQueryBuilderRawSql(this.sqlLimiter);
        this.selectCountWithAlias = dbPlatform.isSelectCountWithAlias();
    }

    protected String getOrderBy(String orderBy, BeanPropertyAssocMany<?> many, BeanDescriptor<?> desc, boolean hasListener) {
        String manyOrderBy = null;
        if (many != null && (manyOrderBy = many.getFetchOrderBy()) != null) {
            manyOrderBy = CQueryBuilder.prefixOrderByFields(many.getName(), manyOrderBy);
        }
        if (orderBy == null && (hasListener || manyOrderBy != null)) {
            StringBuffer sb = new StringBuffer();
            BeanProperty[] uids = desc.propertiesId();
            for (int i = 0; i < uids.length; ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(uids[i].getName());
            }
            orderBy = sb.toString();
        }
        if (manyOrderBy != null) {
            orderBy = orderBy + " , " + manyOrderBy;
        }
        return orderBy;
    }

    public static String prefixOrderByFields(String name, String orderBy) {
        StringBuilder sb = new StringBuilder();
        for (String token : orderBy.split(",")) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(name);
            sb.append(".");
            sb.append(token.trim());
        }
        return sb.toString();
    }

    public <T> CQueryFetchIds buildFetchIdsQuery(OrmQueryRequest<T> request) {
        String sql;
        Query query = request.getQuery();
        query.setSelectId();
        CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
        CQueryPlan queryPlan = request.getQueryPlan();
        if (queryPlan != null) {
            predicates.prepare(false);
            String sql2 = queryPlan.getSql();
            return new CQueryFetchIds(request, predicates, sql2, this.backgroundExecutor);
        }
        if (this.isLuceneSupported(request) && predicates.isLuceneResolvable()) {
            SqlTree sqlTree = this.createLuceneSqlTree(request, predicates);
            queryPlan = new CQueryPlanLucene(request, sqlTree);
            sql = "Lucene Index";
        } else {
            predicates.prepare(true);
            SqlTree sqlTree = this.createSqlTree(request, predicates);
            SqlLimitResponse s = this.buildSql(null, request, predicates, sqlTree);
            sql = s.getSql();
            queryPlan = new CQueryPlan(sql, sqlTree, false, s.isIncludesRowNumberColumn(), predicates.getLogWhereSql());
        }
        request.putQueryPlan(queryPlan);
        return new CQueryFetchIds(request, predicates, sql, this.backgroundExecutor);
    }

    public <T> CQueryRowCount buildRowCountQuery(OrmQueryRequest<T> request) {
        Query query = request.getQuery();
        query.setOrder(null);
        boolean hasMany = !query.getManyWhereJoins().isEmpty();
        query.setSelectId();
        String sqlSelect = "select count(*)";
        if (hasMany) {
            query.setDistinct(true);
            sqlSelect = null;
        }
        CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
        CQueryPlan queryPlan = request.getQueryPlan();
        if (queryPlan != null) {
            predicates.prepare(false);
            String sql = queryPlan.getSql();
            return new CQueryRowCount(request, predicates, sql);
        }
        predicates.prepare(true);
        SqlTree sqlTree = this.createSqlTree(request, predicates);
        SqlLimitResponse s = this.buildSql(sqlSelect, request, predicates, sqlTree);
        String sql = s.getSql();
        if (hasMany) {
            sql = "select count(*) from ( " + sql + ")";
            if (this.selectCountWithAlias) {
                sql = sql + " as c";
            }
        }
        queryPlan = new CQueryPlan(sql, sqlTree, false, s.isIncludesRowNumberColumn(), predicates.getLogWhereSql());
        request.putQueryPlan(queryPlan);
        return new CQueryRowCount(request, predicates, sql);
    }

    private boolean isLuceneSupported(OrmQueryRequest<?> request) {
        if (!this.luceneAvailable) {
            return false;
        }
        Query.UseIndex useIndex = request.getQuery().getUseIndex();
        if (useIndex == null && (useIndex = request.getBeanDescriptor().getUseIndex()) == null) {
            useIndex = this.defaultUseIndex;
        }
        return !Query.UseIndex.NO.equals((Object)useIndex);
    }

    public <T> CQuery<T> buildQuery(OrmQueryRequest<T> request) {
        if (request.isSqlSelect()) {
            return this.sqlSelectBuilder.build(request);
        }
        CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
        CQueryPlan queryPlan = request.getQueryPlan();
        if (queryPlan != null) {
            if (queryPlan.isLucene()) {
                predicates.isLuceneResolvable();
            } else {
                predicates.prepare(false);
            }
            return new CQuery<T>(request, predicates, queryPlan);
        }
        if (this.isLuceneSupported(request) && predicates.isLuceneResolvable()) {
            SqlTree sqlTree = this.createLuceneSqlTree(request, predicates);
            queryPlan = new CQueryPlanLucene(request, sqlTree);
        } else {
            predicates.prepare(true);
            SqlTree sqlTree = this.createSqlTree(request, predicates);
            SqlLimitResponse res = this.buildSql(null, request, predicates, sqlTree);
            boolean rawSql = request.isRawSql();
            queryPlan = rawSql ? new CQueryPlanRawSql(request, res, sqlTree, predicates.getLogWhereSql()) : new CQueryPlan(request, res, sqlTree, rawSql, predicates.getLogWhereSql(), null);
        }
        request.putQueryPlan(queryPlan);
        return new CQuery<T>(request, predicates, queryPlan);
    }

    private SqlTree createSqlTree(OrmQueryRequest<?> request, CQueryPredicates predicates) {
        if (request.isRawSql()) {
            return this.createRawSqlSqlTree(request, predicates);
        }
        return new SqlTreeBuilder(this.tableAliasPlaceHolder, this.columnAliasPrefix, request, predicates).build();
    }

    private SqlTree createLuceneSqlTree(OrmQueryRequest<?> request, CQueryPredicates predicates) {
        LIndex luceneIndex = request.getLuceneIndex();
        OrmQueryDetail ormQueryDetail = luceneIndex.getOrmQueryDetail();
        return new SqlTreeBuilder(request, predicates, ormQueryDetail).build();
    }

    private SqlTree createRawSqlSqlTree(OrmQueryRequest<?> request, CQueryPredicates predicates) {
        BeanDescriptor<?> descriptor = request.getBeanDescriptor();
        RawSql.ColumnMapping columnMapping = request.getQuery().getRawSql().getColumnMapping();
        PathProperties pathProps = new PathProperties();
        Iterator<RawSql.ColumnMapping.Column> it = columnMapping.getColumns();
        while (it.hasNext()) {
            RawSql.ColumnMapping.Column column = it.next();
            String propertyName = column.getPropertyName();
            if ("$$_IGNORE_COLUMN_$$".equals(propertyName)) continue;
            ElPropertyValue el = descriptor.getElGetValue(propertyName);
            if (el == null) {
                String msg = "Property [" + propertyName + "] not found on " + descriptor.getFullName();
                throw new PersistenceException(msg);
            }
            BeanProperty beanProperty = el.getBeanProperty();
            if (beanProperty.isId()) {
                propertyName = SplitName.parent(propertyName);
            } else if (beanProperty instanceof BeanPropertyAssocOne) {
                String msg = "Column [" + column.getDbColumn() + "] mapped to complex Property[" + propertyName + "]";
                msg = msg + ". It should be mapped to a simple property (proably the Id property). ";
                throw new PersistenceException(msg);
            }
            if (propertyName == null) continue;
            String[] pathProp = SplitName.split(propertyName);
            pathProps.addToPath(pathProp[0], pathProp[1]);
        }
        OrmQueryDetail detail = new OrmQueryDetail();
        for (String path : pathProps.getPaths()) {
            Set<String> props = pathProps.get(path);
            detail.getChunk(path, true).setDefaultProperties(null, props);
        }
        return new SqlTreeBuilder(request, predicates, detail).build();
    }

    private SqlLimitResponse buildSql(String selectClause, OrmQueryRequest<?> request, CQueryPredicates predicates, SqlTree select) {
        String dbOrderBy;
        String dbFilterMany;
        String dbWhere;
        Query query = request.getQuery();
        RawSql rawSql = query.getRawSql();
        if (rawSql != null) {
            return this.rawSqlHandler.buildSql(request, predicates, rawSql.getSql());
        }
        BeanPropertyAssocMany<?> manyProp = select.getManyProperty();
        boolean useSqlLimiter = false;
        StringBuilder sb = new StringBuilder(500);
        if (selectClause != null) {
            sb.append(selectClause);
        } else {
            boolean bl = useSqlLimiter = query.hasMaxRowsOrFirstRow() && manyProp == null;
            if (!useSqlLimiter) {
                sb.append("select ");
                if (query.isDistinct()) {
                    sb.append("distinct ");
                }
            }
            sb.append(select.getSelectSql());
        }
        sb.append(" ").append('\n');
        sb.append("from ");
        sb.append(select.getFromSql());
        String inheritanceWhere = select.getInheritanceWhereSql();
        boolean hasWhere = false;
        if (inheritanceWhere.length() > 0) {
            sb.append(" ").append('\n').append("where");
            sb.append(inheritanceWhere);
            hasWhere = true;
        }
        if (request.isFindById() || query.getId() != null) {
            if (hasWhere) {
                sb.append(" and ");
            } else {
                sb.append('\n').append("where ");
            }
            BeanDescriptor<?> desc = request.getBeanDescriptor();
            String idSql = desc.getIdBinderIdSql();
            sb.append(idSql).append(" ");
            hasWhere = true;
        }
        if (!this.isEmpty(dbWhere = predicates.getDbWhere())) {
            if (!hasWhere) {
                hasWhere = true;
                sb.append(" ").append('\n').append("where ");
            } else {
                sb.append("and ");
            }
            sb.append(dbWhere);
        }
        if (!this.isEmpty(dbFilterMany = predicates.getDbFilterMany())) {
            if (!hasWhere) {
                sb.append(" ").append('\n').append("where ");
            } else {
                sb.append("and ");
            }
            sb.append(dbFilterMany);
        }
        if ((dbOrderBy = predicates.getDbOrderBy()) != null) {
            sb.append(" ").append('\n');
            sb.append("order by ").append(dbOrderBy);
        }
        if (useSqlLimiter) {
            OrmQueryLimitRequest r = new OrmQueryLimitRequest(sb.toString(), dbOrderBy, (SpiQuery<?>)query);
            return this.sqlLimiter.limit(r);
        }
        return new SqlLimitResponse(sb.toString(), false);
    }

    private boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}

