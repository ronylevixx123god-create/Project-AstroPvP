/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config;

public interface ExternalTransactionManager {
    public void setTransactionManager(Object var1);

    public Object getCurrentTransaction();
}

