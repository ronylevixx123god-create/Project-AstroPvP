/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanPersister {
    public void insert(PersistRequestBean<?> var1) throws PersistenceException;

    public void update(PersistRequestBean<?> var1) throws PersistenceException;

    public void delete(PersistRequestBean<?> var1) throws PersistenceException;
}

