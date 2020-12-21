/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist;

import java.sql.SQLException;

public interface BatchPostExecute {
    public void checkRowCount(int var1) throws SQLException;

    public void setGeneratedKey(Object var1);

    public void postExecute() throws SQLException;
}

