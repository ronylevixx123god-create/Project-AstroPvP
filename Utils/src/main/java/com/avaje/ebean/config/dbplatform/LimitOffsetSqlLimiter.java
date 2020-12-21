/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config.dbplatform;

import com.avaje.ebean.config.dbplatform.SqlLimitRequest;
import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
import com.avaje.ebean.config.dbplatform.SqlLimiter;

public class LimitOffsetSqlLimiter
implements SqlLimiter {
    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";

    public SqlLimitResponse limit(SqlLimitRequest request) {
        StringBuilder sb = new StringBuilder(512);
        sb.append("select ");
        if (request.isDistinct()) {
            sb.append("distinct ");
        }
        sb.append(request.getDbSql());
        int firstRow = request.getFirstRow();
        int maxRows = request.getMaxRows();
        if (maxRows > 0) {
            ++maxRows;
        }
        sb.append(" ").append('\n').append(LIMIT).append(" ");
        if (maxRows > 0) {
            sb.append(maxRows);
        }
        if (firstRow > 0) {
            sb.append(" ").append(OFFSET).append(" ");
            sb.append(firstRow);
        }
        return new SqlLimitResponse(sb.toString(), false);
    }
}

