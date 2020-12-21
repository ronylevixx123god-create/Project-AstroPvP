/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.api.SpiCallableSql;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import com.avaje.ebeaninternal.server.persist.Binder;
import com.avaje.ebeaninternal.server.persist.PstmtFactory;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.util.BindParamsParser;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

public class ExeCallableSql {
    private static final Logger logger = Logger.getLogger(ExeCallableSql.class.getName());
    private final Binder binder;
    private final PstmtFactory pstmtFactory;

    public ExeCallableSql(Binder binder, PstmtBatch pstmtBatch) {
        this.binder = binder;
        this.pstmtFactory = new PstmtFactory(null);
    }

    public int execute(PersistRequestCallableSql request) {
        SpiTransaction t = request.getTransaction();
        boolean batchThisRequest = t.isBatchThisRequest();
        CallableStatement cstmt = null;
        try {
            cstmt = this.bindStmt(request, batchThisRequest);
            if (batchThisRequest) {
                cstmt.addBatch();
                int n = -1;
                return n;
            }
            int rowCount = request.executeUpdate();
            request.postExecute();
            int n = rowCount;
            return n;
        }
        catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        finally {
            if (!batchThisRequest && cstmt != null) {
                try {
                    cstmt.close();
                }
                catch (SQLException e) {
                    logger.log(Level.SEVERE, null, e);
                }
            }
        }
    }

    private CallableStatement bindStmt(PersistRequestCallableSql request, boolean batchThisRequest) throws SQLException {
        CallableStatement cstmt;
        SpiCallableSql callableSql = request.getCallableSql();
        SpiTransaction t = request.getTransaction();
        String sql = callableSql.getSql();
        BindParams bindParams = callableSql.getBindParams();
        sql = BindParamsParser.parse(bindParams, sql);
        boolean logSql = request.isLogSql();
        if (batchThisRequest) {
            cstmt = this.pstmtFactory.getCstmt(t, logSql, sql, request);
        } else {
            if (logSql) {
                t.logInternal(sql);
            }
            cstmt = this.pstmtFactory.getCstmt(t, sql);
        }
        if (callableSql.getTimeout() > 0) {
            cstmt.setQueryTimeout(callableSql.getTimeout());
        }
        String bindLog = null;
        if (!bindParams.isEmpty()) {
            bindLog = this.binder.bind(bindParams, new DataBind(cstmt));
        }
        request.setBindLog(bindLog);
        request.setBound(bindParams, cstmt);
        return cstmt;
    }
}

