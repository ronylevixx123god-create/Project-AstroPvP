/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.persist.BeanPersister;
import com.avaje.ebeaninternal.server.persist.BeanPersisterFactory;
import com.avaje.ebeaninternal.server.persist.dml.DeleteMeta;
import com.avaje.ebeaninternal.server.persist.dml.DmlBeanPersister;
import com.avaje.ebeaninternal.server.persist.dml.InsertMeta;
import com.avaje.ebeaninternal.server.persist.dml.MetaFactory;
import com.avaje.ebeaninternal.server.persist.dml.UpdateMeta;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DmlBeanPersisterFactory
implements BeanPersisterFactory {
    private final MetaFactory metaFactory;

    public DmlBeanPersisterFactory(DatabasePlatform dbPlatform) {
        this.metaFactory = new MetaFactory(dbPlatform);
    }

    @Override
    public BeanPersister create(BeanDescriptor<?> desc) {
        UpdateMeta updMeta = this.metaFactory.createUpdate(desc);
        DeleteMeta delMeta = this.metaFactory.createDelete(desc);
        InsertMeta insMeta = this.metaFactory.createInsert(desc);
        return new DmlBeanPersister(updMeta, insMeta, delMeta);
    }
}

