/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.Transaction;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.query.CQueryPredicates;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CQueryRowCount {
    private static final Logger logger = Logger.getLogger(CQueryRowCount.class.getName());
    private final OrmQueryRequest<?> request;
    private final BeanDescriptor<?> desc;
    private final SpiQuery<?> query;
    private final CQueryPredicates predicates;
    private final String sql;
    private ResultSet rset;
    private PreparedStatement pstmt;
    private String bindLog;
    private long startNano;
    private int executionTimeMicros;
    private int rowCount;

    public CQueryRowCount(OrmQueryRequest<?> request, CQueryPredicates predicates, String sql) {
        this.request = request;
        this.query = request.getQuery();
        this.sql = sql;
        this.query.setGeneratedSql(sql);
        this.desc = request.getBeanDescriptor();
        this.predicates = predicates;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("FindRowCount exeMicros[").append(this.executionTimeMicros).append("] rows[").append(this.rowCount).append("] type[").append(this.desc.getFullName()).append("] predicates[").append(this.predicates.getLogWhereSql()).append("] bind[").append(this.bindLog).append("]");
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
    public int findRowCount() throws SQLException {
        this.startNano = System.nanoTime();
        try {
            Transaction t = this.request.getTransaction();
            Connection conn = t.getInternalConnection();
            this.pstmt = conn.prepareStatement(this.sql);
            if (this.query.getTimeout() > 0) {
                this.pstmt.setQueryTimeout(this.query.getTimeout());
            }
            this.bindLog = this.predicates.bind(new DataBind(this.pstmt));
            this.rset = this.pstmt.executeQuery();
            if (!this.rset.next()) {
                throw new PersistenceException("Expecting 1 row but got none?");
            }
            this.rowCount = this.rset.getInt(1);
            long exeNano = System.nanoTime() - this.startNano;
            this.executionTimeMicros = (int)exeNano / 1000;
            int n = this.rowCount;
            return n;
        }
        finally {
            this.close();
        }
    }

    private void close() {
        try {
            if (this.rset != null) {
                this.rset.close();
                this.rset = null;
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
}

