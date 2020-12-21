/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.ldap;

import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanManager;
import com.avaje.ebeaninternal.server.ldap.DefaultLdapPersister;
import java.util.Set;
import javax.naming.ldap.LdapName;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LdapPersistBeanRequest<T>
extends PersistRequestBean<T> {
    private final DefaultLdapPersister persister;

    public LdapPersistBeanRequest(SpiEbeanServer server, T bean, Object parentBean, BeanManager<T> mgr, DefaultLdapPersister persister) {
        super(server, bean, parentBean, mgr, null, null);
        this.persister = persister;
    }

    public LdapPersistBeanRequest(SpiEbeanServer server, T bean, Object parentBean, BeanManager<T> mgr, DefaultLdapPersister persister, Set<String> updateProps, ConcurrencyMode concurrencyMode) {
        super(server, bean, parentBean, mgr, null, null, updateProps, concurrencyMode);
        this.persister = persister;
    }

    public LdapName createLdapName() {
        return this.beanDescriptor.createLdapName(this.bean);
    }

    @Override
    public int executeNow() {
        return this.persister.persist(this);
    }

    @Override
    public int executeOrQueue() {
        return this.executeNow();
    }

    @Override
    public void initTransIfRequired() {
    }

    @Override
    public void commitTransIfRequired() {
    }

    @Override
    public void rollbackTransIfRequired() {
    }
}

