/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.persist.BatchedPstmt;
import com.avaje.ebeaninternal.server.persist.BatchedPstmtHolder;
import com.avaje.ebeaninternal.server.persist.dml.PersistHandler;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import com.avaje.ebeaninternal.server.transaction.BeanDelta;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.OptimisticLockException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class DmlHandler
implements PersistHandler,
BindableRequest {
    protected static final Logger logger = Logger.getLogger(DmlHandler.class.getName());
    protected final PersistRequestBean<?> persistRequest;
    protected final StringBuilder bindLog;
    protected final Set<String> loadedProps;
    protected final SpiTransaction transaction;
    protected final boolean emptyStringToNull;
    protected final boolean logLevelSql;
    protected DataBind dataBind;
    protected String sql;
    protected ArrayList<UpdateGenValue> updateGenValues;
    private Set<String> additionalProps;
    private boolean checkDelta;
    private BeanDelta deltaBean;

    protected DmlHandler(PersistRequestBean<?> persistRequest, boolean emptyStringToNull) {
        this.persistRequest = persistRequest;
        this.emptyStringToNull = emptyStringToNull;
        this.loadedProps = persistRequest.getLoadedProperties();
        this.transaction = persistRequest.getTransaction();
        this.logLevelSql = this.transaction.isLogSql();
        this.bindLog = this.logLevelSql ? new StringBuilder() : null;
    }

    protected void setCheckDelta(boolean checkDelta) {
        this.checkDelta = checkDelta;
    }

    @Override
    public PersistRequestBean<?> getPersistRequest() {
        return this.persistRequest;
    }

    @Override
    public abstract void bind() throws SQLException;

    @Override
    public abstract void execute() throws SQLException;

    protected void checkRowCount(int rowCount) throws SQLException, OptimisticLockException {
        try {
            this.persistRequest.checkRowCount(rowCount);
            this.persistRequest.postExecute();
        }
        catch (OptimisticLockException e) {
            String m = e.getMessage() + " sql[" + this.sql + "] bind[" + this.bindLog + "]";
            this.persistRequest.getTransaction().log("OptimisticLockException:" + m);
            throw new OptimisticLockException(m, null, e.getEntity());
        }
    }

    @Override
    public void addBatch() throws SQLException {
        PstmtBatch pstmtBatch = this.persistRequest.getPstmtBatch();
        if (pstmtBatch != null) {
            pstmtBatch.addBatch(this.dataBind.getPstmt());
        } else {
            this.dataBind.getPstmt().addBatch();
        }
    }

    @Override
    public void close() {
        try {
            if (this.dataBind != null) {
                this.dataBind.close();
            }
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getBindLog() {
        return this.bindLog == null ? "" : this.bindLog.toString();
    }

    @Override
    public void setIdValue(Object idValue) {
        this.persistRequest.setBoundId(idValue);
    }

    protected void logBinding() {
        if (this.logLevelSql) {
            this.transaction.logInternal(this.bindLog.toString());
        }
    }

    protected void logSql(String sql) {
        if (this.logLevelSql) {
            this.transaction.logInternal(sql);
        }
    }

    @Override
    public boolean isIncluded(BeanProperty prop) {
        return this.loadedProps == null || this.loadedProps.contains(prop.getName());
    }

    @Override
    public boolean isIncludedWhere(BeanProperty prop) {
        if (prop.isDbEncrypted()) {
            return this.isIncluded(prop);
        }
        return prop.isDbUpdatable() && (this.loadedProps == null || this.loadedProps.contains(prop.getName()));
    }

    @Override
    public Object bind(String propName, Object value, int sqlType) throws SQLException {
        if (this.logLevelSql) {
            this.bindLog.append(propName).append("=");
            this.bindLog.append(value).append(", ");
        }
        this.dataBind.setObject(value, sqlType);
        return value;
    }

    @Override
    public Object bindNoLog(Object value, int sqlType, String logPlaceHolder) throws SQLException {
        if (this.logLevelSql) {
            this.bindLog.append(logPlaceHolder).append(" ");
        }
        this.dataBind.setObject(value, sqlType);
        return value;
    }

    @Override
    public Object bind(Object value, BeanProperty prop, String propName, boolean bindNull) throws SQLException {
        return this.bindInternal(this.logLevelSql, value, prop, propName, bindNull);
    }

    @Override
    public Object bindNoLog(Object value, BeanProperty prop, String propName, boolean bindNull) throws SQLException {
        return this.bindInternal(false, value, prop, propName, bindNull);
    }

    private Object bindInternal(boolean log, Object value, BeanProperty prop, String propName, boolean bindNull) throws SQLException {
        if (!bindNull && this.emptyStringToNull && value instanceof String && ((String)value).length() == 0) {
            value = null;
        }
        if (!bindNull && value == null) {
            if (log) {
                this.bindLog.append(propName).append("=");
                this.bindLog.append("null, ");
            }
        } else {
            if (log) {
                this.bindLog.append(propName).append("=");
                if (prop.isLob()) {
                    this.bindLog.append("[LOB]");
                } else {
                    String sv = String.valueOf(value);
                    if (sv.length() > 50) {
                        sv = sv.substring(0, 47) + "...";
                    }
                    this.bindLog.append(sv);
                }
                this.bindLog.append(", ");
            }
            prop.bind(this.dataBind, value);
            if (this.checkDelta && !prop.isId() && prop.isDeltaRequired()) {
                if (this.deltaBean == null) {
                    this.deltaBean = this.persistRequest.createDeltaBean();
                    this.transaction.getEvent().addBeanDelta(this.deltaBean);
                }
                this.deltaBean.add(prop, value);
            }
        }
        return value;
    }

    protected void bindLogAppend(String comment) {
        if (this.logLevelSql) {
            this.bindLog.append(comment);
        }
    }

    @Override
    public final void registerAdditionalProperty(String propertyName) {
        if (this.loadedProps != null && !this.loadedProps.contains(propertyName)) {
            if (this.additionalProps == null) {
                this.additionalProps = new HashSet<String>();
            }
            this.additionalProps.add(propertyName);
        }
    }

    protected void setAdditionalProperties() {
        if (this.additionalProps != null) {
            this.additionalProps.addAll(this.loadedProps);
            this.persistRequest.setLoadedProps(this.additionalProps);
        }
    }

    @Override
    public void registerUpdateGenValue(BeanProperty prop, Object bean, Object value) {
        if (this.updateGenValues == null) {
            this.updateGenValues = new ArrayList();
        }
        this.updateGenValues.add(new UpdateGenValue(prop, bean, value));
        this.registerAdditionalProperty(prop.getName());
    }

    public void setUpdateGenValues() {
        if (this.updateGenValues != null) {
            for (int i = 0; i < this.updateGenValues.size(); ++i) {
                UpdateGenValue updGenVal = this.updateGenValues.get(i);
                updGenVal.setValue();
            }
        }
    }

    protected PreparedStatement getPstmt(SpiTransaction t, String sql, boolean genKeys) throws SQLException {
        Connection conn = t.getInternalConnection();
        if (genKeys) {
            int[] columns = new int[]{1};
            return conn.prepareStatement(sql, columns);
        }
        return conn.prepareStatement(sql);
    }

    protected PreparedStatement getPstmt(SpiTransaction t, String sql, PersistRequestBean<?> request, boolean genKeys) throws SQLException {
        BatchedPstmtHolder batch = t.getBatchControl().getPstmtHolder();
        PreparedStatement stmt = batch.getStmt(sql, request);
        if (stmt != null) {
            return stmt;
        }
        if (this.logLevelSql) {
            t.logInternal(sql);
        }
        stmt = this.getPstmt(t, sql, genKeys);
        PstmtBatch pstmtBatch = request.getPstmtBatch();
        if (pstmtBatch != null) {
            pstmtBatch.setBatchSize(stmt, t.getBatchControl().getBatchSize());
        }
        BatchedPstmt bs = new BatchedPstmt(stmt, genKeys, sql, request.getPstmtBatch(), true);
        batch.addStmt(bs, request);
        return stmt;
    }

    private static final class UpdateGenValue {
        private final BeanProperty property;
        private final Object bean;
        private final Object value;

        private UpdateGenValue(BeanProperty property, Object bean, Object value) {
            this.property = property;
            this.bean = bean;
            this.value = value;
        }

        private void setValue() {
            this.property.setValueIntercept(this.bean, this.value);
        }
    }
}

