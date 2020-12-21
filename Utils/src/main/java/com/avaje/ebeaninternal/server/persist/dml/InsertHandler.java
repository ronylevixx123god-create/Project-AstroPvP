/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebean.Transaction;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.Message;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.persist.DmlUtil;
import com.avaje.ebeaninternal.server.persist.dml.DmlHandler;
import com.avaje.ebeaninternal.server.persist.dml.InsertMeta;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class InsertHandler
extends DmlHandler {
    private final InsertMeta meta;
    private final boolean concatinatedKey;
    private boolean useGeneratedKeys;
    private String selectLastInsertedId;

    public InsertHandler(PersistRequestBean<?> persist, InsertMeta meta) {
        super(persist, meta.isEmptyStringToNull());
        this.meta = meta;
        this.concatinatedKey = meta.isConcatinatedKey();
    }

    @Override
    public boolean isIncluded(BeanProperty prop) {
        return prop.isDbInsertable() && super.isIncluded(prop);
    }

    @Override
    public void bind() throws SQLException {
        PreparedStatement pstmt;
        boolean withId;
        Object bean;
        BeanDescriptor desc = this.persistRequest.getBeanDescriptor();
        Object idValue = desc.getId(bean = this.persistRequest.getBean());
        boolean bl = withId = !DmlUtil.isNullOrZero(idValue);
        if (!withId) {
            if (this.concatinatedKey) {
                withId = this.meta.deriveConcatenatedId(this.persistRequest);
            } else if (this.meta.supportsGetGeneratedKeys()) {
                this.useGeneratedKeys = true;
            } else {
                this.selectLastInsertedId = this.meta.getSelectLastInsertedId();
            }
        }
        Transaction t = this.persistRequest.getTransaction();
        boolean isBatch = t.isBatchThisRequest();
        this.sql = this.meta.getSql(withId);
        if (isBatch) {
            pstmt = this.getPstmt((SpiTransaction)t, this.sql, this.persistRequest, this.useGeneratedKeys);
        } else {
            this.logSql(this.sql);
            pstmt = this.getPstmt((SpiTransaction)t, this.sql, this.useGeneratedKeys);
        }
        this.dataBind = new DataBind(pstmt);
        this.bindLogAppend("Binding Insert [");
        this.bindLogAppend(desc.getBaseTable());
        this.bindLogAppend("]  set[");
        this.meta.bind(this, bean, withId);
        this.bindLogAppend("]");
        this.logBinding();
    }

    @Override
    protected PreparedStatement getPstmt(SpiTransaction t, String sql, boolean useGeneratedKeys) throws SQLException {
        Connection conn = t.getInternalConnection();
        if (useGeneratedKeys) {
            return conn.prepareStatement(sql, 1);
        }
        return conn.prepareStatement(sql);
    }

    @Override
    public void execute() throws SQLException, OptimisticLockException {
        int rc = this.dataBind.executeUpdate();
        if (this.useGeneratedKeys) {
            this.getGeneratedKeys();
        } else if (this.selectLastInsertedId != null) {
            this.fetchGeneratedKeyUsingSelect();
        }
        this.checkRowCount(rc);
        this.setAdditionalProperties();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void getGeneratedKeys() throws SQLException {
        block9: {
            ResultSet rset = this.dataBind.getPstmt().getGeneratedKeys();
            try {
                if (rset.next()) {
                    Object idValue = rset.getObject(1);
                    if (idValue != null) {
                        this.persistRequest.setGeneratedKey(idValue);
                    }
                    break block9;
                }
                throw new PersistenceException(Message.msg("persist.autoinc.norows"));
            }
            finally {
                try {
                    rset.close();
                }
                catch (SQLException ex) {
                    String msg = "Error closing rset for returning generatedKeys?";
                    logger.log(Level.WARNING, msg, ex);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void fetchGeneratedKeyUsingSelect() throws SQLException {
        block15: {
            Connection conn = this.transaction.getConnection();
            PreparedStatement stmt = null;
            ResultSet rset = null;
            try {
                stmt = conn.prepareStatement(this.selectLastInsertedId);
                rset = stmt.executeQuery();
                if (rset.next()) {
                    Object idValue = rset.getObject(1);
                    if (idValue != null) {
                        this.persistRequest.setGeneratedKey(idValue);
                    }
                    break block15;
                }
                throw new PersistenceException(Message.msg("persist.autoinc.norows"));
            }
            finally {
                String msg;
                try {
                    if (rset != null) {
                        rset.close();
                    }
                }
                catch (SQLException ex) {
                    msg = "Error closing rset for fetchGeneratedKeyUsingSelect?";
                    logger.log(Level.WARNING, msg, ex);
                }
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
                catch (SQLException ex) {
                    msg = "Error closing stmt for fetchGeneratedKeyUsingSelect?";
                    logger.log(Level.WARNING, msg, ex);
                }
            }
        }
    }
}

