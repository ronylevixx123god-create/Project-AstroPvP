/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.sql;

import java.sql.Connection;

public interface DataSourcePoolListener {
    public void onAfterBorrowConnection(Connection var1);

    public void onBeforeReturnConnection(Connection var1);
}

