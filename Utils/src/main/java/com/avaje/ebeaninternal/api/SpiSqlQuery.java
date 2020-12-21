/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlQueryListener;
import com.avaje.ebeaninternal.api.BindParams;
import java.sql.PreparedStatement;

public interface SpiSqlQuery
extends SqlQuery {
    public BindParams getBindParams();

    public String getQuery();

    public SqlQueryListener getListener();

    public int getFirstRow();

    public int getMaxRows();

    public int getBackgroundFetchAfter();

    public String getMapKey();

    public int getTimeout();

    public int getBufferFetchSizeHint();

    public boolean isFutureFetch();

    public void setFutureFetch(boolean var1);

    public void setPreparedStatement(PreparedStatement var1);

    public boolean isCancelled();
}

