/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.type.DataReader;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface DbReadContext {
    public int getParentState();

    public void propagateState(Object var1);

    public DataReader getDataReader();

    public boolean isVanillaMode();

    public boolean isRawSql();

    public ReferenceOptions getReferenceOptionsFor(BeanPropertyAssocOne<?> var1);

    public void setCurrentPrefix(String var1, Map<String, String> var2);

    public boolean isAutoFetchProfiling();

    public void profileBean(EntityBeanIntercept var1, String var2);

    public PersistenceContext getPersistenceContext();

    public void register(String var1, EntityBeanIntercept var2);

    public void register(String var1, BeanCollection<?> var2);

    public BeanPropertyAssocMany<?> getManyProperty();

    public void setLoadedBean(Object var1, Object var2);

    public void setLoadedManyBean(Object var1);

    public SpiQuery.Mode getQueryMode();
}

