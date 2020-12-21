/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanDescriptorMap {
    public String getServerName();

    public ServerCacheManager getCacheManager();

    public <T> BeanDescriptor<T> getBeanDescriptor(Class<T> var1);

    public EncryptKey getEncryptKey(String var1, String var2);

    public IdBinder createIdBinder(BeanProperty[] var1);
}

