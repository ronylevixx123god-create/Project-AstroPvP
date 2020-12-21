/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.persist.dml.DmlHandler;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import java.sql.SQLException;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SpiUpdatePlan {
    public boolean isEmptySetClause();

    public void bindSet(DmlHandler var1, Object var2) throws SQLException;

    public long getTimeCreated();

    public Long getTimeLastUsed();

    public Integer getKey();

    public ConcurrencyMode getMode();

    public String getSql();

    public Bindable getSet();

    public Set<String> getProperties();
}

