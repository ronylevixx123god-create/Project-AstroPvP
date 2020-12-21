/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.event;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Transaction;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanPersistRequest<T> {
    public EbeanServer getEbeanServer();

    public Transaction getTransaction();

    public Set<String> getLoadedProperties();

    public Set<String> getUpdatedProperties();

    public T getBean();

    public T getOldValues();
}

