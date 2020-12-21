/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.persist.BeanPersister;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanManager<T> {
    private final BeanPersister persister;
    private final BeanDescriptor<T> descriptor;

    public BeanManager(BeanDescriptor<T> descriptor, BeanPersister persister) {
        this.descriptor = descriptor;
        this.persister = persister;
    }

    public BeanPersister getBeanPersister() {
        return this.persister;
    }

    public BeanDescriptor<T> getBeanDescriptor() {
        return this.descriptor;
    }

    public boolean isLdapEntityType() {
        return this.descriptor.isLdapEntityType();
    }
}

