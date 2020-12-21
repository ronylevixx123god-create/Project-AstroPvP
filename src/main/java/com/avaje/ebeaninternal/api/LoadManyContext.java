/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.LoadSecondaryQuery;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface LoadManyContext
extends LoadSecondaryQuery {
    public void configureQuery(SpiQuery<?> var1);

    public String getFullPath();

    public ObjectGraphNode getObjectGraphNode();

    public PersistenceContext getPersistenceContext();

    public int getBatchSize();

    public BeanDescriptor<?> getBeanDescriptor();

    public BeanPropertyAssocMany<?> getBeanProperty();
}

