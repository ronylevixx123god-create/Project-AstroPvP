/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.RawSql;
import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.query.CQueryPlan;
import com.avaje.ebeaninternal.server.query.SqlTree;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.RsetDataReaderIndexed;
import java.sql.ResultSet;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CQueryPlanRawSql
extends CQueryPlan {
    private final int[] rsetIndexPositions;

    public CQueryPlanRawSql(OrmQueryRequest<?> request, SqlLimitResponse sqlRes, SqlTree sqlTree, String logWhereSql) {
        super(request, sqlRes, sqlTree, true, logWhereSql, null);
        this.rsetIndexPositions = this.createIndexPositions(request, sqlTree);
    }

    @Override
    public DataReader createDataReader(ResultSet rset) {
        return new RsetDataReaderIndexed(rset, this.rsetIndexPositions, this.isRowNumberIncluded());
    }

    private int[] createIndexPositions(OrmQueryRequest<?> request, SqlTree sqlTree) {
        List<String> chain = sqlTree.buildSelectExpressionChain();
        RawSql.ColumnMapping columnMapping = request.getQuery().getRawSql().getColumnMapping();
        int[] indexPositions = new int[chain.size()];
        for (int i = 0; i < chain.size(); ++i) {
            int indexPos;
            String expr = chain.get(i);
            indexPositions[i] = indexPos = 1 + columnMapping.getIndexPosition(expr);
        }
        return indexPositions;
    }
}

