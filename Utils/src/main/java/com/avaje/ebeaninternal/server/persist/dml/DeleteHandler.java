/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebean.Transaction;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.persist.dml.DeleteMeta;
import com.avaje.ebeaninternal.server.persist.dml.DmlHandler;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.persistence.OptimisticLockException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DeleteHandler
extends DmlHandler {
    private final DeleteMeta meta;

    public DeleteHandler(PersistRequestBean<?> persist, DeleteMeta meta) {
        super(persist, meta.isEmptyStringAsNull());
        this.meta = meta;
    }

    @Override
    public void bind() throws SQLException {
        PreparedStatement pstmt;
        this.sql = this.meta.getSql(this.persistRequest);
        Transaction t = this.persistRequest.getTransaction();
        boolean isBatch = t.isBatchThisRequest();
        if (isBatch) {
            pstmt = this.getPstmt((SpiTransaction)t, this.sql, this.persistRequest, false);
        } else {
            this.logSql(this.sql);
            pstmt = this.getPstmt((SpiTransaction)t, this.sql, false);
        }
        this.dataBind = new DataBind(pstmt);
        this.bindLogAppend("Binding Delete [");
        this.bindLogAppend(this.meta.getTableName());
        this.bindLogAppend("] where[");
        this.meta.bind(this.persistRequest, this);
        this.bindLogAppend("]");
        this.logBinding();
    }

    @Override
    public void execute() throws SQLException, OptimisticLockException {
        int rowCount = this.dataBind.executeUpdate();
        this.checkRowCount(rowCount);
    }

    @Override
    public boolean isIncluded(BeanProperty prop) {
        return prop.isDbUpdatable() && super.isIncluded(prop);
    }

    @Override
    public boolean isIncludedWhere(BeanProperty prop) {
        return prop.isDbUpdatable() && (this.loadedProps == null || this.loadedProps.contains(prop.getName()));
    }
}

