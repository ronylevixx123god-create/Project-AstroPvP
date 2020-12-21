/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.LoadSecondaryQuery;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface LoadBeanContext
extends LoadSecondaryQuery {
    public void configureQuery(SpiQuery<?> var1, String var2);

    public String getFullPath();

    public PersistenceContext getPersistenceContext();

    public BeanDescriptor<?> getBeanDescriptor();

    public int getBatchSize();
}

