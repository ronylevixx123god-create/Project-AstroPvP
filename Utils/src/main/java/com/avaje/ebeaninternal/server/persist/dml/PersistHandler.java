/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dml;

import java.sql.SQLException;

public interface PersistHandler {
    public String getBindLog();

    public void bind() throws SQLException;

    public void addBatch() throws SQLException;

    public void execute() throws SQLException;

    public void close() throws SQLException;
}

