/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebean.Transaction;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.SpiUpdatePlan;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.persist.dml.DmlHandler;
import com.avaje.ebeaninternal.server.persist.dml.UpdateMeta;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import javax.persistence.OptimisticLockException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class UpdateHandler
extends DmlHandler {
    private final UpdateMeta meta;
    private Set<String> updatedProperties;
    private boolean emptySetClause;

    public UpdateHandler(PersistRequestBean<?> persist, UpdateMeta meta) {
        super(persist, meta.isEmptyStringAsNull());
        this.meta = meta;
    }

    @Override
    public void bind() throws SQLException {
        PreparedStatement pstmt;
        SpiUpdatePlan updatePlan = this.meta.getUpdatePlan(this.persistRequest);
        if (updatePlan.isEmptySetClause()) {
            this.emptySetClause = true;
            return;
        }
        this.updatedProperties = updatePlan.getProperties();
        this.sql = updatePlan.getSql();
        Transaction t = this.persistRequest.getTransaction();
        boolean isBatch = t.isBatchThisRequest();
        if (isBatch) {
            pstmt = this.getPstmt((SpiTransaction)t, this.sql, this.persistRequest, false);
        } else {
            this.logSql(this.sql);
            pstmt = this.getPstmt((SpiTransaction)t, this.sql, false);
        }
        this.dataBind = new DataBind(pstmt);
        this.bindLogAppend("Binding Update [");
        this.bindLogAppend(this.meta.getTableName());
        this.bindLogAppend("] ");
        this.meta.bind(this.persistRequest, this, updatePlan);
        this.setUpdateGenValues();
        this.bindLogAppend("]");
        this.logBinding();
    }

    @Override
    public void addBatch() throws SQLException {
        if (!this.emptySetClause) {
            super.addBatch();
        }
    }

    @Override
    public void execute() throws SQLException, OptimisticLockException {
        if (!this.emptySetClause) {
            int rowCount = this.dataBind.executeUpdate();
            this.checkRowCount(rowCount);
            this.setAdditionalProperties();
        }
    }

    @Override
    public boolean isIncluded(BeanProperty prop) {
        return prop.isDbUpdatable() && (this.updatedProperties == null || this.updatedProperties.contains(prop.getName()));
    }
}

