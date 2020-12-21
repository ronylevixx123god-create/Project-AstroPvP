/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebeaninternal.api.SpiUpdatePlan;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.persist.dml.DmlHandler;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import java.sql.SQLException;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class UpdatePlan
implements SpiUpdatePlan {
    public static final UpdatePlan EMPTY_SET_CLAUSE = new UpdatePlan();
    private final Integer key;
    private final ConcurrencyMode mode;
    private final String sql;
    private final Bindable set;
    private final Set<String> properties;
    private final boolean checkIncludes;
    private final long timeCreated;
    private final boolean emptySetClause;
    private Long timeLastUsed;

    public UpdatePlan(ConcurrencyMode mode, String sql, Bindable set) {
        this(null, mode, sql, set, null);
    }

    public UpdatePlan(Integer key, ConcurrencyMode mode, String sql, Bindable set, Set<String> properties) {
        this.emptySetClause = false;
        this.key = key;
        this.mode = mode;
        this.sql = sql;
        this.set = set;
        this.properties = properties;
        this.checkIncludes = properties != null;
        this.timeCreated = System.currentTimeMillis();
    }

    private UpdatePlan() {
        this.emptySetClause = true;
        this.key = 0;
        this.mode = ConcurrencyMode.NONE;
        this.sql = null;
        this.set = null;
        this.properties = null;
        this.checkIncludes = false;
        this.timeCreated = 0L;
    }

    @Override
    public boolean isEmptySetClause() {
        return this.emptySetClause;
    }

    @Override
    public void bindSet(DmlHandler bind, Object bean) throws SQLException {
        Long touched;
        this.set.dmlBind(bind, this.checkIncludes, bean);
        this.timeLastUsed = touched = Long.valueOf(System.currentTimeMillis());
    }

    @Override
    public long getTimeCreated() {
        return this.timeCreated;
    }

    @Override
    public Long getTimeLastUsed() {
        return this.timeLastUsed;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public ConcurrencyMode getMode() {
        return this.mode;
    }

    @Override
    public String getSql() {
        return this.sql;
    }

    @Override
    public Bindable getSet() {
        return this.set;
    }

    @Override
    public Set<String> getProperties() {
        return this.properties;
    }
}

