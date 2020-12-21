/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PstmtBatch {
    public void setBatchSize(PreparedStatement var1, int var2);

    public void addBatch(PreparedStatement var1) throws SQLException;

    public int executeBatch(PreparedStatement var1, int var2, String var3, boolean var4) throws SQLException;
}

