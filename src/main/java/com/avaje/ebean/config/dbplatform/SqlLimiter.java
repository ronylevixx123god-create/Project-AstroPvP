/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config.dbplatform;

import com.avaje.ebean.config.dbplatform.SqlLimitRequest;
import com.avaje.ebean.config.dbplatform.SqlLimitResponse;

public interface SqlLimiter {
    public static final char NEW_LINE = '\n';
    public static final char CARRIAGE_RETURN = '\r';

    public SqlLimitResponse limit(SqlLimitRequest var1);
}

