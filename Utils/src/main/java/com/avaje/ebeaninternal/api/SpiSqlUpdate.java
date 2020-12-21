/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.SqlUpdate;
import com.avaje.ebeaninternal.api.BindParams;

public interface SpiSqlUpdate
extends SqlUpdate {
    public BindParams getBindParams();
}

