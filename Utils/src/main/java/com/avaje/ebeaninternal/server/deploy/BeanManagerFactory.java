/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanManager;
import com.avaje.ebeaninternal.server.persist.BeanPersister;
import com.avaje.ebeaninternal.server.persist.BeanPersisterFactory;
import com.avaje.ebeaninternal.server.persist.dml.DmlBeanPersisterFactory;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanManagerFactory {
    final BeanPersisterFactory peristerFactory;

    public BeanManagerFactory(ServerConfig config, DatabasePlatform dbPlatform) {
        this.peristerFactory = new DmlBeanPersisterFactory(dbPlatform);
    }

    public <T> BeanManager<T> create(BeanDescriptor<T> desc) {
        BeanPersister persister = this.peristerFactory.create(desc);
        return new BeanManager<T>(desc, persister);
    }
}

