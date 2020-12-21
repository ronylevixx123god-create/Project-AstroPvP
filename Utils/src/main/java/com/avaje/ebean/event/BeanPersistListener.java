/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.event;

import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanPersistListener<T> {
    public boolean inserted(T var1);

    public boolean updated(T var1, Set<String> var2);

    public boolean deleted(T var1);

    public void remoteInsert(Object var1);

    public void remoteUpdate(Object var1);

    public void remoteDelete(Object var1);
}

