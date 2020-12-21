/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config;

import java.sql.PreparedStatement;

public interface PstmtDelegate {
    public PreparedStatement unwrap(PreparedStatement var1);
}

