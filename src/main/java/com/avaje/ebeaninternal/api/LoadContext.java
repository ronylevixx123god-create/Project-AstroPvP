/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.LoadBeanContext;
import com.avaje.ebeaninternal.api.LoadManyContext;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface LoadContext {
    public int getSecondaryQueriesMinBatchSize(OrmQueryRequest<?> var1, int var2);

    public void executeSecondaryQueries(OrmQueryRequest<?> var1, int var2);

    public void registerSecondaryQueries(SpiQuery<?> var1);

    public boolean isUseAutofetchManager();

    public ObjectGraphNode getObjectGraphNode(String var1);

    public int getParentState();

    public PersistenceContext getPersistenceContext();

    public void setPersistenceContext(PersistenceContext var1);

    public void register(String var1, EntityBeanIntercept var2);

    public void register(String var1, BeanCollection<?> var2);

    public LoadBeanContext getBeanContext(String var1);

    public LoadManyContext getManyContext(String var1);
}

