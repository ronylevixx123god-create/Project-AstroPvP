/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.SqlFutureList;
import com.avaje.ebean.SqlQueryListener;
import com.avaje.ebean.SqlRow;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SqlQuery
extends Serializable {
    public void cancel();

    public List<SqlRow> findList();

    public Set<SqlRow> findSet();

    public Map<?, SqlRow> findMap();

    public SqlRow findUnique();

    public SqlFutureList findFutureList();

    public SqlQuery setParameter(String var1, Object var2);

    public SqlQuery setParameter(int var1, Object var2);

    public SqlQuery setListener(SqlQueryListener var1);

    public SqlQuery setFirstRow(int var1);

    public SqlQuery setMaxRows(int var1);

    public SqlQuery setBackgroundFetchAfter(int var1);

    public SqlQuery setMapKey(String var1);

    public SqlQuery setTimeout(int var1);

    public SqlQuery setBufferFetchSizeHint(int var1);
}

