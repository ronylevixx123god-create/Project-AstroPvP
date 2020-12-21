/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PersistRequest;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.server.persist.BeanPersister;
import com.avaje.ebeaninternal.server.persist.dml.DeleteHandler;
import com.avaje.ebeaninternal.server.persist.dml.DeleteMeta;
import com.avaje.ebeaninternal.server.persist.dml.InsertHandler;
import com.avaje.ebeaninternal.server.persist.dml.InsertMeta;
import com.avaje.ebeaninternal.server.persist.dml.PersistHandler;
import com.avaje.ebeaninternal.server.persist.dml.UpdateHandler;
import com.avaje.ebeaninternal.server.persist.dml.UpdateMeta;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DmlBeanPersister
implements BeanPersister {
    private static final Logger logger = Logger.getLogger(DmlBeanPersister.class.getName());
    private final UpdateMeta updateMeta;
    private final InsertMeta insertMeta;
    private final DeleteMeta deleteMeta;

    public DmlBeanPersister(UpdateMeta updateMeta, InsertMeta insertMeta, DeleteMeta deleteMeta) {
        this.updateMeta = updateMeta;
        this.insertMeta = insertMeta;
        this.deleteMeta = deleteMeta;
    }

    @Override
    public void delete(PersistRequestBean<?> request) {
        DeleteHandler delete = new DeleteHandler(request, this.deleteMeta);
        this.execute(request, delete);
    }

    @Override
    public void insert(PersistRequestBean<?> request) {
        InsertHandler insert = new InsertHandler(request, this.insertMeta);
        this.execute(request, insert);
    }

    @Override
    public void update(PersistRequestBean<?> request) {
        UpdateHandler update = new UpdateHandler(request, this.updateMeta);
        this.execute(request, update);
    }

    private void execute(PersistRequest request, PersistHandler handler) {
        SpiTransaction trans = request.getTransaction();
        boolean batchThisRequest = trans.isBatchThisRequest();
        try {
            handler.bind();
            if (batchThisRequest) {
                handler.addBatch();
            } else {
                handler.execute();
            }
        }
        catch (SQLException e) {
            String errMsg = StringHelper.replaceStringMulti(e.getMessage(), new String[]{"\r", "\n"}, "\\n ");
            String msg = "ERROR executing DML bindLog[" + handler.getBindLog() + "] error[" + errMsg + "]";
            if (request.getTransaction().isLogSummary()) {
                request.getTransaction().logInternal(msg);
            }
            throw new PersistenceException(msg, e);
        }
        finally {
            if (!batchThisRequest && handler != null) {
                try {
                    handler.close();
                }
                catch (SQLException e) {
                    logger.log(Level.SEVERE, null, e);
                }
            }
        }
    }
}

