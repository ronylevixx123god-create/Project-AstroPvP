/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.BeanIdList;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.query.BackgroundIdFetch;
import com.avaje.ebeaninternal.server.query.CQueryPredicates;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.RsetDataReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CQueryFetchIds {
    private static final Logger logger = Logger.getLogger(CQueryFetchIds.class.getName());
    private final OrmQueryRequest<?> request;
    private final BeanDescriptor<?> desc;
    private final SpiQuery<?> query;
    private final BackgroundExecutor backgroundExecutor;
    private final CQueryPredicates predicates;
    private final String sql;
    private RsetDataReader dataReader;
    private PreparedStatement pstmt;
    private String bindLog;
    private long startNano;
    private int executionTimeMicros;
    private int rowCount;
    private final int maxRows;
    private final int bgFetchAfter;

    public CQueryFetchIds(OrmQueryRequest<?> request, CQueryPredicates predicates, String sql, BackgroundExecutor backgroundExecutor) {
        this.backgroundExecutor = backgroundExecutor;
        this.request = request;
        this.query = request.getQuery();
        this.sql = sql;
        this.maxRows = this.query.getMaxRows();
        this.bgFetchAfter = this.query.getBackgroundFetchAfter();
        this.query.setGeneratedSql(sql);
        this.desc = request.getBeanDescriptor();
        this.predicates = predicates;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("FindIds exeMicros[").append(this.executionTimeMicros).append("] rows[").append(this.rowCount).append("] type[").append(this.desc.getName()).append("] predicates[").append(this.predicates.getLogWhereSql()).append("] bind[").append(this.bindLog).append("]");
        return sb.toString();
    }

    public String getBindLog() {
        return this.bindLog;
    }

    public String getGeneratedSql() {
        return this.sql;
    }

    public SpiOrmQueryRequest<?> getQueryRequest() {
        return this.request;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BeanIdList findIds() throws SQLException {
        boolean useBackgroundToContinueFetch = false;
        this.startNano = System.nanoTime();
        try {
            List<Object> idList = this.query.getIdList();
            if (idList == null) {
                idList = Collections.synchronizedList(new ArrayList());
                this.query.setIdList(idList);
            }
            BeanIdList result = new BeanIdList(idList);
            Transaction t = this.request.getTransaction();
            Connection conn = t.getInternalConnection();
            this.pstmt = conn.prepareStatement(this.sql);
            if (this.query.getBufferFetchSizeHint() > 0) {
                this.pstmt.setFetchSize(this.query.getBufferFetchSizeHint());
            }
            if (this.query.getTimeout() > 0) {
                this.pstmt.setQueryTimeout(this.query.getTimeout());
            }
            this.bindLog = this.predicates.bind(new DataBind(this.pstmt));
            ResultSet rset = this.pstmt.executeQuery();
            this.dataReader = new RsetDataReader(rset);
            boolean hitMaxRows = false;
            boolean hasMoreRows = false;
            this.rowCount = 0;
            DbContext ctx = new DbContext();
            while (rset.next()) {
                Object idValue = this.desc.getIdBinder().read(ctx);
                idList.add(idValue);
                this.dataReader.resetColumnPosition();
                ++this.rowCount;
                if (this.maxRows > 0 && this.rowCount == this.maxRows) {
                    hitMaxRows = true;
                    hasMoreRows = rset.next();
                    break;
                }
                if (this.bgFetchAfter <= 0 || this.rowCount < this.bgFetchAfter) continue;
                useBackgroundToContinueFetch = true;
                break;
            }
            if (hitMaxRows) {
                result.setHasMore(hasMoreRows);
            }
            if (useBackgroundToContinueFetch) {
                this.request.setBackgroundFetching();
                BackgroundIdFetch bgFetch = new BackgroundIdFetch((SpiTransaction)t, rset, this.pstmt, ctx, this.desc, result);
                FutureTask<Integer> future = new FutureTask<Integer>(bgFetch);
                this.backgroundExecutor.execute(future);
                result.setBackgroundFetch(future);
            }
            long exeNano = System.nanoTime() - this.startNano;
            this.executionTimeMicros = (int)exeNano / 1000;
            BeanIdList beanIdList = result;
            return beanIdList;
        }
        finally {
            if (!useBackgroundToContinueFetch) {
                this.close();
            }
        }
    }

    private void close() {
        try {
            if (this.dataReader != null) {
                this.dataReader.close();
                this.dataReader = null;
            }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, null, e);
        }
        try {
            if (this.pstmt != null) {
                this.pstmt.close();
                this.pstmt = null;
            }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class DbContext
    implements DbReadContext {
        DbContext() {
        }

        @Override
        public int getParentState() {
            return 0;
        }

        @Override
        public void propagateState(Object e) {
            throw new RuntimeException("Not Called");
        }

        @Override
        public SpiQuery.Mode getQueryMode() {
            return SpiQuery.Mode.NORMAL;
        }

        @Override
        public DataReader getDataReader() {
            return CQueryFetchIds.this.dataReader;
        }

        @Override
        public boolean isVanillaMode() {
            return false;
        }

        public boolean isSharedInstance() {
            return false;
        }

        public boolean isReadOnly() {
            return false;
        }

        @Override
        public boolean isRawSql() {
            return false;
        }

        @Override
        public void register(String path, EntityBeanIntercept ebi) {
        }

        @Override
        public void register(String path, BeanCollection<?> bc) {
        }

        @Override
        public ReferenceOptions getReferenceOptionsFor(BeanPropertyAssocOne<?> beanProp) {
            return null;
        }

        @Override
        public BeanPropertyAssocMany<?> getManyProperty() {
            return null;
        }

        @Override
        public PersistenceContext getPersistenceContext() {
            return null;
        }

        @Override
        public boolean isAutoFetchProfiling() {
            return false;
        }

        @Override
        public void profileBean(EntityBeanIntercept ebi, String prefix) {
        }

        @Override
        public void setCurrentPrefix(String currentPrefix, Map<String, String> pathMap) {
        }

        @Override
        public void setLoadedBean(Object loadedBean, Object id) {
        }

        @Override
        public void setLoadedManyBean(Object loadedBean) {
        }
    }
}

